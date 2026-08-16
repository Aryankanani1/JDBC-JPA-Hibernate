package org.example.JPA.Pagination.Using_JPQL;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.JPA.Important_Annotation_For_Entity.User;

import java.util.List;
import java.util.Map;

/**
 * Pagination with JPQL: setFirstResult (offset) + setMaxResults (limit).
 */
public class Pagination {
    public static void main(String[] args) {
        // Credentials come from env vars so no real password lives in persistence.xml.
        Map<String, String> config = Map.of(
                "jakarta.persistence.jdbc.user", System.getenv().getOrDefault("DB_USERNAME", "root"),
                "jakarta.persistence.jdbc.password", System.getenv().getOrDefault("DB_PASSWORD", "")
        );
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit", config);
        EntityManager em = emf.createEntityManager();

        // Seed 25 users once (only if the table is empty).
        long count = em.createQuery("SELECT COUNT(u) FROM AnnotatedUser u", Long.class).getSingleResult();
        if (count == 0) {
            em.getTransaction().begin();
            for (int i = 1; i <= 25; i++) {
                em.persist(new User("User " + i, "user" + i + "@example.com"));
            }
            em.getTransaction().commit();
        }

        // Page 1, 10 rows per page.
        int pageNumber = 1;
        int pageSize = 10;
        List<User> page = em.createQuery("SELECT u FROM AnnotatedUser u ORDER BY u.id", User.class)
                .setFirstResult((pageNumber - 1) * pageSize)   // offset
                .setMaxResults(pageSize)                        // limit
                .getResultList();

        System.out.println("Page " + pageNumber + " (" + page.size() + " rows):");
        page.forEach(u -> System.out.println(" - " + u.getName()));

        em.close();
        emf.close();
    }
}

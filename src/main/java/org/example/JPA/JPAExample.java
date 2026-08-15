package org.example.JPA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Map;

public class JPAExample {

    public static void main(String[] args) {
        // Credentials come from env vars so no real password lives in persistence.xml.
        Map<String, String> config = Map.of(
                "jakarta.persistence.jdbc.user", System.getenv().getOrDefault("DB_USERNAME", "root"),
                "jakarta.persistence.jdbc.password", System.getenv().getOrDefault("DB_PASSWORD", "")
        );

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPersistenceUnit", config);
        EntityManager em = emf.createEntityManager();

        // Save a user
        em.getTransaction().begin();
        em.persist(new User("Tony Stark", "tony@example.com"));
        em.getTransaction().commit();

        // Read all users
        em.createQuery("SELECT u FROM User u", User.class)
                .getResultList()
                .forEach(System.out::println);

        em.close();
        emf.close();
    }
}

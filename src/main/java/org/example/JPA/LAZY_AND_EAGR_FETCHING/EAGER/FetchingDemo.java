package org.example.JPA.LAZY_AND_EAGR_FETCHING.EAGER;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Map;

/**
 * EAGER fetching: the employees collection is loaded together with the
 * department, so it can still be read after the EntityManager is closed.
 */
public class FetchingDemo {
    public static void main(String[] args) {
        Map<String, String> config = Map.of(
                "jakarta.persistence.jdbc.user", System.getenv().getOrDefault("DB_USERNAME", "root"),
                "jakarta.persistence.jdbc.password", System.getenv().getOrDefault("DB_PASSWORD", "")
        );
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit", config);

        // Seed one department with two employees.
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Department dept = new Department("Engineering");
        dept.addEmployee(new Employee("Alice"));
        dept.addEmployee(new Employee("Bob"));
        em.persist(dept);
        em.getTransaction().commit();
        Long deptId = dept.getId();
        em.close();

        // Load the department, then close the EntityManager (entity becomes detached).
        EntityManager em2 = emf.createEntityManager();
        Department loaded = em2.find(Department.class, deptId);
        em2.close();

        // employees was loaded eagerly with the department -> still readable after close.
        System.out.println("Employees count: " + loaded.getEmployees().size());
        loaded.getEmployees().forEach(e -> System.out.println(" - " + e.getName()));

        emf.close();
    }
}

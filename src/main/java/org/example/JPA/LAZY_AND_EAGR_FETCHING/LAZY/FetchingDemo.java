package org.example.JPA.LAZY_AND_EAGR_FETCHING.LAZY;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Map;

/**
 * LAZY fetching: the employees collection is NOT loaded with the department.
 * Accessing it after the EntityManager is closed fails with
 * LazyInitializationException.
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

        // employees was never loaded and the EM is closed -> LAZY access fails.
        try {
            System.out.println("Employees count: " + loaded.getEmployees().size());
        } catch (Exception e) {
            System.out.println("LAZY -> accessing employees after close failed: "
                    + e.getClass().getSimpleName());
        }

        emf.close();
    }
}

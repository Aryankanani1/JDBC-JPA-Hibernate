package org.example.JPA.CRUD_operations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Map;

public class UserService {
    public static void main(String[] args) {
        // Credentials come from env vars so no real password lives in persistence.xml.
        Map<String, String> config = Map.of(
                "jakarta.persistence.jdbc.user", System.getenv().getOrDefault("DB_USERNAME", "root"),
                "jakarta.persistence.jdbc.password", System.getenv().getOrDefault("DB_PASSWORD", "")
        );
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("myPersistenceUnit", config);

        // CREATE
        EntityManager createManager = entityManagerFactory.createEntityManager();
        createManager.getTransaction().begin();
        User user = new User();
        user.setName("Hulk");
        user.setEmail("hulk@example.com");
        createManager.persist(user);
        createManager.getTransaction().commit();
        Long userId = user.getId();          // id is generated on persist/commit
        createManager.close();
        System.out.println("Created: " + user);

        // READ
        EntityManager readManager = entityManagerFactory.createEntityManager();
        User found = readManager.find(User.class, userId);
        readManager.close();
        System.out.println("Read:    " + found);

        // UPDATE
        EntityManager updateManager = entityManagerFactory.createEntityManager();
        updateManager.getTransaction().begin();
        User toUpdate = updateManager.find(User.class, userId);
        if (toUpdate != null) {
            toUpdate.setName("Robert");      // change the MANAGED entity
        }
        updateManager.getTransaction().commit();   // commit flushes the change
        updateManager.close();
        System.out.println("Updated: " + toUpdate);

        // DELETE
        EntityManager deleteManager = entityManagerFactory.createEntityManager();
        deleteManager.getTransaction().begin();
        User toDelete = deleteManager.find(User.class, userId);
        if (toDelete != null) {
            deleteManager.remove(toDelete);
        }
        deleteManager.getTransaction().commit();
        deleteManager.close();
        System.out.println("Deleted user with id " + userId);

        // Verify deletion
        EntityManager verifyManager = entityManagerFactory.createEntityManager();
        User afterDelete = verifyManager.find(User.class, userId);
        verifyManager.close();
        System.out.println("After delete, find returns: " + afterDelete);

        entityManagerFactory.close();
    }
}

package org.example.Hibernate.HibernateExample;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Classic Hibernate (native SessionFactory) CRUD, using hibernate.cfg.xml.
 * Credentials come from DB_USERNAME / DB_PASSWORD so none live in the config.
 */
public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class);
        configuration.setProperty("hibernate.connection.username",
                System.getenv().getOrDefault("DB_USERNAME", "root"));
        configuration.setProperty("hibernate.connection.password",
                System.getenv().getOrDefault("DB_PASSWORD", ""));

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {

            // CREATE
            Student student = new Student("robert", 22);
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.persist(student);
                session.getTransaction().commit();
            }
            int id = student.getId();
            System.out.println("Created: " + student);

            // READ
            try (Session session = sessionFactory.openSession()) {
                Student found = session.get(Student.class, id);
                System.out.println("Read:    " + found);
            }

            // UPDATE
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                Student toUpdate = session.get(Student.class, id);
                toUpdate.setAge(23);                 // managed entity -> flushed on commit
                session.getTransaction().commit();
                System.out.println("Updated: " + toUpdate);
            }

            // DELETE
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                Student toDelete = session.get(Student.class, id);
                session.remove(toDelete);
                session.getTransaction().commit();
                System.out.println("Deleted student with id " + id);
            }
        }
    }
}

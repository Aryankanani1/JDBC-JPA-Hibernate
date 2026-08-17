package org.example.Hibernate.Inheritance_Mapping.single_table_inheritance;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Persists a fiction and a non-fiction book, then reads the whole hierarchy
 * back with a single polymorphic query ("from Book").
 */
public class SingleTableDemo {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(FictionBook.class)
                .addAnnotatedClass(NonFictionBook.class);
        configuration.setProperty("hibernate.connection.username",
                System.getenv().getOrDefault("DB_USERNAME", "root"));
        configuration.setProperty("hibernate.connection.password",
                System.getenv().getOrDefault("DB_PASSWORD", ""));

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            session.persist(new FictionBook("Dune", "sci-fi"));
            session.persist(new NonFictionBook("Sapiens", "history"));
            session.getTransaction().commit();

            System.out.println("All books (polymorphic query):");
            session.createQuery("from Book", Book.class)
                    .getResultList()
                    .forEach(b -> System.out.println(" - " + b));
        }
    }
}

package org.example.Hibernate.Get_and_Load;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * get() vs load():
 *   - get()          hits the database immediately and returns the entity (or null).
 *   - load()/getReference() returns a lazy proxy; the SELECT is deferred until a
 *     property is actually accessed.
 */
public class HibernateExample {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class);
        configuration.setProperty("hibernate.connection.username",
                System.getenv().getOrDefault("DB_USERNAME", "root"));
        configuration.setProperty("hibernate.connection.password",
                System.getenv().getOrDefault("DB_PASSWORD", ""));

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {

            // Seed two books so there is something to fetch.
            Long id1, id2;
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                Book b1 = new Book("Clean Code");
                Book b2 = new Book("The Pragmatic Programmer");
                session.persist(b1);
                session.persist(b2);
                session.getTransaction().commit();
                id1 = b1.getId();
                id2 = b2.getId();
            }

            // get(): SELECT runs right away.
            try (Session session = sessionFactory.openSession()) {
                Book book = session.get(Book.class, id1);
                System.out.println("get()  -> " + book);
            }

            // load() (getReference): proxy first, SELECT only on property access.
            try (Session session = sessionFactory.openSession()) {
                Book proxy = session.getReference(Book.class, id2);
                System.out.println("load() -> proxy created, no SELECT yet");
                System.out.println("load() -> accessing title triggers SELECT: " + proxy.getTitle());
            }
        }
    }
}

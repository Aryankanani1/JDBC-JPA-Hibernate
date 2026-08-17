package org.example.Hibernate.Inheritance_Mapping.joined;

import jakarta.persistence.*;

/**
 * JOINED: a base table holds the common columns and each subclass gets its own
 * table joined by the shared primary key.
 */
@Entity
@Table(name = "jt_book")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}

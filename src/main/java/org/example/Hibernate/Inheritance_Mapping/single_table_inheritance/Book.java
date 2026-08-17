package org.example.Hibernate.Inheritance_Mapping.single_table_inheritance;

import jakarta.persistence.*;

/**
 * SINGLE_TABLE: the whole hierarchy lives in one table, and a discriminator
 * column (book_type) records which subclass each row is.
 */
@Entity
@Table(name = "st_book")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "book_type", discriminatorType = DiscriminatorType.STRING)
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

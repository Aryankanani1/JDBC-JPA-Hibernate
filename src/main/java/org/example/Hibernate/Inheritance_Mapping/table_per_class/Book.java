package org.example.Hibernate.Inheritance_Mapping.table_per_class;

import jakarta.persistence.*;

/**
 * TABLE_PER_CLASS: each concrete subclass gets its own standalone table that
 * repeats the common columns. IDENTITY can't be used here (ids must be unique
 * across sibling tables), so a TABLE generator is used instead.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
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

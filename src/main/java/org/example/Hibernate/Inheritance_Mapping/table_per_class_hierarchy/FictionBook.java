package org.example.Hibernate.Inheritance_Mapping.table_per_class_hierarchy;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "jt_fiction_book")
public class FictionBook extends Book {

    private String genre;

    public FictionBook() {
    }

    public FictionBook(String title, String genre) {
        super(title);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "FictionBook{title='" + getTitle() + "', genre='" + genre + "'}";
    }
}

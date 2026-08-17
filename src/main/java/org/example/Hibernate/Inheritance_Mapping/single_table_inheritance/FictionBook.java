package org.example.Hibernate.Inheritance_Mapping.single_table_inheritance;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("fiction")
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

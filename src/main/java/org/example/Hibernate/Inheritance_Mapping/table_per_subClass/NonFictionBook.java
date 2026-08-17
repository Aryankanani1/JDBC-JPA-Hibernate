package org.example.Hibernate.Inheritance_Mapping.table_per_subClass;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tpc_non_fiction_book")
public class NonFictionBook extends Book {

    private String topic;

    public NonFictionBook() {
    }

    public NonFictionBook(String title, String topic) {
        super(title);
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    @Override
    public String toString() {
        return "NonFictionBook{title='" + getTitle() + "', topic='" + topic + "'}";
    }
}

package org.example.Hibernate.Inheritance_Mapping.single_table_inheritance;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("non_fiction")
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

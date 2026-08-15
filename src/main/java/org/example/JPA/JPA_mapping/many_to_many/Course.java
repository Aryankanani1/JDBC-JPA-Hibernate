package org.example.JPA.JPA_mapping.many_to_many;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class Course {
    private Long id;
    private String title;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;



}


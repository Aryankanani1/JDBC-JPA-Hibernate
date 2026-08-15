package org.example.JPA.JPA_mapping.many_to_many;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Many-to-many (inverse side): a Course is taken by many Students.
 * mappedBy points to Student.courses, which owns the join table.
 */
@Entity
@Table(name = "mtm_course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<>();

    public Course() {
    }

    public Course(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Student> getStudents() {
        return students;
    }
}

package org.example.JPA.JPA_mapping.many_to_many;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Many-to-many (owning side): a Student takes many Courses.
 * This side defines the join table that links the two.
 */
@Entity
@Table(name = "mtm_student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "mtm_student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new ArrayList<>();

    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    // Keeps both sides of the relationship in sync.
    public void addCourse(Course course) {
        courses.add(course);
        course.getStudents().add(this);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }
}

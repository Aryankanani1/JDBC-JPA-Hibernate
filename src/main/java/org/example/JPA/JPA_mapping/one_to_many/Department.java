package org.example.JPA.JPA_mapping.one_to_many;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Department {
    private Long id;
    private String name;
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}

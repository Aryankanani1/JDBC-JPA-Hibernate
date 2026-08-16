package org.example.JPA.LAZY_AND_EAGR_FETCHING.LAZY;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "LazyDepartment")
@Table(name = "lazy_department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // LAZY: employees are fetched only when the collection is first accessed.
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    // Keeps both sides of the relationship in sync.
    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setDepartment(this);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}

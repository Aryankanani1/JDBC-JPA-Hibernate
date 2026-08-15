package org.example.JPA.JPA_mapping.many_to_one;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * One-to-many (inverse side): one Department has many Employees.
 * mappedBy points to Employee.department, which owns the foreign key.
 */
@Entity
@Table(name = "mto_department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}

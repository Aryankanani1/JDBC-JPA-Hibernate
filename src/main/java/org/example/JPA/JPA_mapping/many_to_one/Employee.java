package org.example.JPA.JPA_mapping.many_to_one;

import jakarta.persistence.*;

/**
 * Many-to-one (owning side): many Employees belong to one Department.
 * The foreign key (department_id) lives on this table.
 */
@Entity
@Table(name = "mto_employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Employee() {
    }

    public Employee(String name) {
        this.name = name;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}

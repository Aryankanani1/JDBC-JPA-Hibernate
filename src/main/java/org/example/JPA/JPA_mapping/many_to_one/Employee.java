package org.example.JPA.JPA_mapping.many_to_one;

import jakarta.persistence.*;
import org.example.JPA.JPA_mapping.one_to_many.Department;

import javax.xml.namespace.QName;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}

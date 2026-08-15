package org.example.JPA.JPA_mapping.one_to_one;

import jakarta.persistence.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    @JoinColumn(name = "parking_pass_id")
    private ParkingPass parkingPass;

}

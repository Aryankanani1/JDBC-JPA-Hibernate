package org.example.JPA.JPA_mapping.one_to_one;

import jakarta.persistence.*;

/**
 * One-to-one (owning side): each Employee has exactly one ParkingPass.
 * The foreign key (parking_pass_id) lives on this table.
 */
@Entity
@Table(name = "oto_employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parking_pass_id")
    private ParkingPass parkingPass;

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

    public ParkingPass getParkingPass() {
        return parkingPass;
    }

    public void setParkingPass(ParkingPass parkingPass) {
        this.parkingPass = parkingPass;
    }
}

package org.example.JPA.JPA_mapping.one_to_one;

import jakarta.persistence.*;

/**
 * One-to-one (inverse side): mapped by Employee.parkingPass, so no
 * foreign key column is created on this table.
 */
@Entity
@Table(name = "oto_parking_pass")
public class ParkingPass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    @OneToOne(mappedBy = "parkingPass")
    private Employee employee;

    public ParkingPass() {
    }

    public ParkingPass(String location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}

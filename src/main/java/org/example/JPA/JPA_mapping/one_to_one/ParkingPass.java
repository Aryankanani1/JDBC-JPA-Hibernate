package org.example.JPA.JPA_mapping.one_to_one;

import jakarta.persistence.*;
import org.hibernate.bytecode.enhance.spi.EnhancementInfo;

@Entity
public class ParkingPass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;

    @OneToOne(mappedBy = "parkingPass")
    private Employee employee;

}

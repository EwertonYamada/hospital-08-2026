package com.hospital.doctor.model;

import com.hospital.ward.enums.Specialty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "crm", unique = true)
    private String crm;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    @Column(nullable = false)
    private boolean active = true;
}

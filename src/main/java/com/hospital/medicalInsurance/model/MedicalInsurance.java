package com.hospital.medicalInsurance.model;

import com.hospital.medicalInsurance.enums.MedicalInsuranceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@Table(name = "medicalInsurance")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalInsurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private MedicalInsuranceType type;
    @Column(nullable = false)
    private BigDecimal coverageRate;
}

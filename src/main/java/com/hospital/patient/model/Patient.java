package com.hospital.patient.model;

import com.hospital.medicalInsurance.model.MedicalInsurance;
import com.hospital.patient.dto.PatientRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "document")
    private String document;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "is_hospitalized")
    private boolean isHospitalized = false;

    @ManyToOne
    @JoinColumn(name = "medical_insurance_id")
    private MedicalInsurance medicalInsurance;

    public Patient(PatientRequest patientRequest) {
        this.name = patientRequest.name();
        this.document = patientRequest.document();
        this.birthDate = patientRequest.birthDate();
    }
}

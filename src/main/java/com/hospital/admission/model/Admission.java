package com.hospital.admission.model;

import com.hospital.admission.enums.AdmissionStatus;
import com.hospital.bed.model.Bed;
import com.hospital.admission.enums.EventType;
import com.hospital.doctor.model.Doctor;
import com.hospital.exam.model.Exam;
import com.hospital.patient.model.Patient;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Admission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bed_id")
    private Bed bed;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToMany(mappedBy = "admission")
    private List<Exam> exams = new ArrayList<>();

    @Column(name = "admitted_at")
    private Date admittedAt;

    @Column(name = "discharged_at")
    private Date dischargedAt;

    @ManyToMany
    @JoinTable(
            name = "admission_doctor",
            joinColumns = @JoinColumn(name = "admission_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private List<Doctor> doctors = new ArrayList<>();

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AdmissionStatus status;

    public Admission(Bed bed, Patient patient) {
        this.bed = bed;
        this.patient = patient;
        this.admittedAt = new Date();
        this.dischargedAt = null;
        this.status = AdmissionStatus.ACTIVE;
    }
}

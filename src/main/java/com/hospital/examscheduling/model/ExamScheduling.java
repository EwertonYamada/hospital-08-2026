package com.hospital.examscheduling.model;

import com.hospital.admission.model.Admission;
import com.hospital.doctor.model.Doctor;
import com.hospital.examscheduling.enums.ExamStatus;
import com.hospital.examscheduling.enums.ExamType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "exam")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamScheduling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String nameExam;

    @Enumerated(EnumType.STRING)
    private ExamType examType;

    @Enumerated(EnumType.STRING)
    private ExamStatus examStatus;

    @ManyToOne
    @JoinColumn(name = "admission_id", nullable = false)
    private Admission admission;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

}

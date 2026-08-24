package com.hospital.exam.model;

import com.hospital.admission.model.Admission;
import com.hospital.exam.enums.ExamStatus;
import com.hospital.exam.enums.ExamType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "exam")
public class ExamModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(nullable = false)
    private String nomeExame;

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

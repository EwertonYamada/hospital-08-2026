package com.hospital.servicesprovided.model;

import com.hospital.admission.model.Admission;
import com.hospital.daily.model.Daily;
import com.hospital.doctor.model.Doctor;
import com.hospital.drug.model.Drug;
import com.hospital.exam.model.Exam;
import com.hospital.servicesprovided.enums.ServicesType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicesProvided {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admission_id", nullable = false)
    private Admission admission;

    @ManyToOne
    @JoinColumn(name = "responsible_doctor_id")
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    private ServicesType type;

    private Integer count;

    private BigDecimal unitValue;

    private BigDecimal totalValue;

    @ManyToOne
    @JoinColumn(name = "drug_id")
    private Drug drug;

    @ManyToOne
    @JoinColumn(name = "daily_id")
    private Daily daily;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}

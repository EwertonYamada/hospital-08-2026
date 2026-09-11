package com.hospital.logVisits.model;

import com.hospital.admission.model.Admission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogVisits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String document;
    @Column(nullable = false)
    private LocalDateTime dateTimeIn;
    private LocalDateTime dateTimeOut;
    @ManyToOne
    @JoinColumn(name = "admission_id", nullable = false)
    private Admission admission;


}

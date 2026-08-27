package com.hospital.examscheduling.repository;

import com.hospital.examscheduling.model.ExamScheduling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ExamSchedulingRepository extends JpaRepository<ExamScheduling, Long> {
    boolean existsByAdmission_Patient_IdAndDate(Long patientId, LocalDateTime date);

    boolean existsByAdmission_Patient_IdAndDateAndIdNot(Long patientId, LocalDateTime date, Long id);
}

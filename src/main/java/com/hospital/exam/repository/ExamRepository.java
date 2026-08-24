package com.hospital.exam.repository;

import com.hospital.exam.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    boolean existsByAdmission_Patient_IdAndDate(Long patientId, LocalDateTime date);

    boolean existsByAdmission_Patient_IdAndDateAndIdNot(Long patientId, LocalDateTime date, Long id);
}

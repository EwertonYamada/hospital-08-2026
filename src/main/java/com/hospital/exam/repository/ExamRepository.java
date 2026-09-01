package com.hospital.exam.repository;

import com.hospital.exam.model.Exam;
import com.hospital.examscheduling.enums.ExamType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam,Long> {

    boolean existsByType(ExamType type);

    Optional<Exam> findByType(ExamType type);
}

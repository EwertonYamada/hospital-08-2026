package com.hospital.exam.repository;

import com.hospital.exam.model.Exam;
import com.hospital.examscheduling.enums.ExamType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam,Long> {

    boolean existsByType(ExamType type);
}

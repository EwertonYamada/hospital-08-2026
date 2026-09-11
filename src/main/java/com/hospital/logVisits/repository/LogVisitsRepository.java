package com.hospital.logVisits.repository;

import com.hospital.logVisits.model.LogVisits;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogVisitsRepository extends JpaRepository<LogVisits, Long> {
    boolean existsByAdmission_IdAndDateTimeOutIsNull(Long admissionId);
}

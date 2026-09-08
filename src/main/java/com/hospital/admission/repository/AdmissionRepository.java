package com.hospital.admission.repository;

import com.hospital.admission.enums.AdmissionStatus;
import com.hospital.admission.model.Admission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {
    List<Admission> findByStatus(AdmissionStatus status);
}

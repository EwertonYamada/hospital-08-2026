package com.hospital.medicalInsurance.repository;

import com.hospital.medicalInsurance.model.MedicalInsurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalInsuranceRepository extends JpaRepository<MedicalInsurance, Long> {
}

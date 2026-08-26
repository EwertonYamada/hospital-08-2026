package com.hospital.medicalInsurance.repository;

import com.hospital.medicalInsurance.enums.MedicalInsuranceType;
import com.hospital.medicalInsurance.model.MedicalInsurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalInsuranceRepository extends JpaRepository<MedicalInsurance, Long> {
    boolean existsByType(MedicalInsuranceType type);
    boolean existsByTypeAndIdNot(MedicalInsuranceType type);
}

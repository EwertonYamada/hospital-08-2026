package com.hospital.hospital.repository;

import com.hospital.hospital.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Boolean existsHospitalByCnpj(String cnpj);
}

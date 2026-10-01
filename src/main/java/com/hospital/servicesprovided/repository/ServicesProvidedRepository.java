package com.hospital.servicesprovided.repository;

import com.hospital.servicesprovided.enums.ServicesType;
import com.hospital.servicesprovided.model.ServicesProvided;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ServicesProvidedRepository extends JpaRepository<ServicesProvided, Long> {

    boolean existsByAdmission_IdAndTypeAndCreatedAtBetween(Long admissionId, ServicesType type, LocalDateTime start, LocalDateTime end);

    List<ServicesProvided> findByAdmission_Id(Long admissionId);
}

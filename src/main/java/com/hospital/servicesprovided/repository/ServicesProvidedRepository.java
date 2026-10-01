package com.hospital.servicesprovided.repository;

import com.hospital.servicesprovided.enums.ServicesType;
import com.hospital.servicesprovided.model.ServicesProvided;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ServicesProvidedRepository extends JpaRepository<ServicesProvided, Long> {

    boolean existsByAdmission_IdAndTypeAndCreatedAtBetween(Long admissionId, ServicesType type, LocalDateTime start, LocalDateTime end);

}

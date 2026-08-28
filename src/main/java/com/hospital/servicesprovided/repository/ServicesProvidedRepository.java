package com.hospital.servicesprovided.repository;

import com.hospital.servicesprovided.model.ServicesProvided;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicesProvidedRepository extends JpaRepository<ServicesProvided, Long> {
}

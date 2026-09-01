package com.hospital.servicesprovidedclosure.repository;

import com.hospital.servicesprovidedclosure.model.ServicesProvidedClosure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesProvidedClosureRepository extends JpaRepository<ServicesProvidedClosure,Long> {

    boolean existsByAdmissionId(Long admissionId);
}

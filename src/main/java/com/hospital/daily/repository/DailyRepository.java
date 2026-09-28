package com.hospital.daily.repository;

import com.hospital.bed.enums.BedType;
import com.hospital.daily.model.Daily;
import com.hospital.ward.enums.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DailyRepository extends JpaRepository<Daily, Long> {

    boolean existsByTypeAndSpecialty(BedType bedType, Specialty specialty);

    Optional<Daily> findByTypeAndSpecialty(BedType bedType, Specialty specialty);
}

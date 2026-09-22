package com.hospital.drug.repository;

import com.hospital.drug.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {

    boolean existsByCode(String code);
}

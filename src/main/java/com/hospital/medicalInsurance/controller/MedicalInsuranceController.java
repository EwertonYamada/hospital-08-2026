package com.hospital.medicalInsurance.controller;

import com.hospital.medicalInsurance.dto.MedicalInsuranceRequestDTO;
import com.hospital.medicalInsurance.dto.MedicalInsuranceResponseDTO;
import com.hospital.medicalInsurance.service.MedicalInsuranceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicalInsurance")
public class MedicalInsuranceController {

    private final MedicalInsuranceService medicalInsuranceService;

    public MedicalInsuranceController(MedicalInsuranceService medicalInsuranceService) {
        this.medicalInsuranceService = medicalInsuranceService;
    }

    @PostMapping
    public ResponseEntity<MedicalInsuranceResponseDTO> create(@RequestBody @Valid MedicalInsuranceRequestDTO dto) {
        MedicalInsuranceResponseDTO medicalInsuranceResponseDTO = medicalInsuranceService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicalInsuranceResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalInsuranceResponseDTO> findById(@PathVariable Long id) {
        MedicalInsuranceResponseDTO medicalInsuranceResponseDTO = medicalInsuranceService.findById(id);
        return ResponseEntity.ok(medicalInsuranceResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<MedicalInsuranceResponseDTO>> findAll() {
        List<MedicalInsuranceResponseDTO> medicalInsuranceResponseDTOList = medicalInsuranceService.findAll();
        return ResponseEntity.ok(medicalInsuranceResponseDTOList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalInsuranceResponseDTO> update(@PathVariable Long id, @RequestBody @Valid MedicalInsuranceRequestDTO dto) {
        MedicalInsuranceResponseDTO medicalInsuranceResponseDTO = medicalInsuranceService.update(id, dto);
        return ResponseEntity.ok(medicalInsuranceResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        medicalInsuranceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

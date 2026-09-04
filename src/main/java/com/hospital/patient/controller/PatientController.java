package com.hospital.patient.controller;

import com.hospital.patient.dto.PatientRequest;
import com.hospital.patient.model.Patient;
import com.hospital.patient.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<Patient> create(@RequestBody PatientRequest patient) {
        return ResponseEntity.ok(this.patientService.create(patient));
    }

    @PatchMapping("/{patientId}/medicalinsurance/{medicalInsuranceId}")
    public ResponseEntity<Patient> linkMedicalInsurance(@PathVariable Long patientId, @PathVariable Long medicalInsuranceId) {
        Patient patient = patientService.linkMedicalInsurance(patientId, medicalInsuranceId);
        return ResponseEntity.ok(patient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> findById(@PathVariable Long id) {
        Patient patient = patientService.getById(id);
        return ResponseEntity.ok(patient);
    }

    @GetMapping
    public ResponseEntity<List<Patient>> findAll() {
        List<Patient> patient = patientService.findAll();
        return ResponseEntity.ok(patient);
    }
}

package com.hospital.patient.controller;

import com.hospital.patient.dto.PatientRequest;
import com.hospital.patient.model.Patient;
import com.hospital.patient.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

package com.hospital.patient.service;

import com.hospital.medicalInsurance.model.MedicalInsurance;
import com.hospital.medicalInsurance.repository.MedicalInsuranceRepository;
import com.hospital.medicalInsurance.service.MedicalInsuranceService;
import com.hospital.patient.dto.PatientRequest;
import com.hospital.patient.model.Patient;
import com.hospital.patient.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final MedicalInsuranceRepository medicalInsuranceRepository;

    public PatientService(PatientRepository patientRepository,MedicalInsuranceRepository medicalInsuranceRepository) {
        this.patientRepository = patientRepository;
        this.medicalInsuranceRepository = medicalInsuranceRepository;
    }

    @Transactional
    public Patient create(PatientRequest patientRequest) {
        Patient patient = new Patient(patientRequest);
        return this.save(patient);
    }

    public Patient getById(Long id) {
        return this.patientRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Patient with id " + id + " not found"));
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Patient getPatientToAdmission(Long patientId) {
        Patient patient = this.getById(patientId);
        if (patient.isHospitalized()) throw new RuntimeException("Patient already is hospitalized");
        return patient;
    }

    public Patient getPatientToDischarge(Long patientId) {
        Patient patient = this.getById(patientId);
        if (!patient.isHospitalized()) throw new RuntimeException("Patient is note hospitalized");
        return patient;
    }

    public Patient save(Patient patient) {
        return this.patientRepository.save(patient);
    }

    public boolean existsPatientWithMedicalInsurance(Long medicalInsuranceId) {
        return patientRepository.existsByMedicalInsurance_Id(medicalInsuranceId);
    }

    public Patient linkMedicalInsurance(Long patientId, Long medicalInsuranceId) {
        Patient patient = getById(patientId);
        MedicalInsurance medicalInsurance = medicalInsuranceRepository.findById(medicalInsuranceId)
                .orElseThrow(() -> new EntityNotFoundException("Convenio com id " + medicalInsuranceId + " nao encontrado"));
        patient.setMedicalInsurance(medicalInsurance);
        return save(patient);
    }
}
package com.hospital.admission.service;

import com.hospital.admission.enums.AdmissionStatus;
import com.hospital.bed.enums.BedStatus;
import com.hospital.bed.model.Bed;
import com.hospital.bed.service.BedService;
import com.hospital.admission.dto.AdmissionRequest;
import com.hospital.admission.model.Admission;
import com.hospital.admission.repository.AdmissionRepository;
import com.hospital.doctor.model.Doctor;
import com.hospital.doctor.service.DoctorService;
import com.hospital.patient.model.Patient;
import com.hospital.patient.service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service
public class AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final PatientService patientService;
    private final BedService bedService;
    private  final DoctorService doctorService;

    public AdmissionService(
            AdmissionRepository admissionRepository,
            PatientService patientService,
            BedService bedService, DoctorService doctorService
    ) {
        this.admissionRepository = admissionRepository;
        this.patientService = patientService;
        this.bedService = bedService;
        this.doctorService = doctorService;
    }

    @Transactional
    public Admission admission(AdmissionRequest request) {
        Admission admission = this.prepareAdmission(request);
        this.admissionRepository.save(admission);
        this.updateBed(admission.getBed(), BedStatus.OCCUPIED);
        this.updatePatient(admission.getPatient(), Boolean.TRUE);
        return admission;
    }

    private void updatePatient(Patient patient, Boolean isHospitalized) {
        patient.setHospitalized(isHospitalized);
        this.patientService.save(patient);
    }

    private void updateBed(Bed bed, BedStatus bedStatus) {
        bed.setStatus(bedStatus);
        this.bedService.save(bed);
    }

    private Admission prepareAdmission(AdmissionRequest request) {
        Patient patient = this.patientService.getPatientToAdmission(request.patientId());
        Bed bed = this.bedService.getAvailableBedById(request.bedId());
        return new Admission(bed, patient);
    }

    public Admission getById(Long admissionId) {
        return this.admissionRepository.findById(admissionId).orElseThrow(() ->
                new EntityNotFoundException("Admission with id " + admissionId + " not found"));
    }


    public Admission discharge(Long admissionId) {
        Admission admission = this.getById(admissionId);
        this.validateAdmissionBeforeDischarge(admission);
        admission.setDischargedAt(new Date());
        admission.setStatus(AdmissionStatus.INACTIVE);
        this.admissionRepository.save(admission);
        this.updateBed(admission.getBed(), BedStatus.IN_PREPARATION);
        this.updatePatient(admission.getPatient(), Boolean.FALSE);
        return admission;
    }

    private void validateAdmissionBeforeDischarge(Admission admission) {
        if (Objects.nonNull(admission.getDischargedAt()) || AdmissionStatus.INACTIVE.equals(admission.getStatus()))
            throw new RuntimeException("The patient with id " +admission.getPatient().getId() + " has already been discharged.");
    }

    public void validateAdmissionIsActive(AdmissionStatus status) {
        if (status != AdmissionStatus.ACTIVE) {
            throw new RuntimeException("Paciente não está hospitalizado");
        }
    }

    public Admission vincularMedico(Long admissionId, Long medicoId) {
        Admission admission = this.getById(admissionId);
        if (!admission.getStatus().equals(AdmissionStatus.ACTIVE)) {
            throw new RuntimeException("Internacao inativa");
        }
        Doctor doctor = this.doctorService.getById(medicoId);
        if (!admission.getDoctors().contains(doctor)) {
            admission.getDoctors().add(doctor);
            this.admissionRepository.save(admission);
        }
        return  admission;
    }

    public void validateDoctorIsResponsibleForAdmission(Admission admission, Doctor doctor) {
        if (!admission.getDoctors().contains(doctor)) {
            throw new RuntimeException("O médico informado não é responsável por essa internação");
        }
    }
}
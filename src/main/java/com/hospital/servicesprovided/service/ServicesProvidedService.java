package com.hospital.servicesprovided.service;

import com.hospital.admission.model.Admission;
import com.hospital.admission.service.AdmissionService;
import com.hospital.doctor.model.Doctor;
import com.hospital.doctor.service.DoctorService;
import com.hospital.servicesprovided.dto.ServicesProvidedRequestDTO;
import com.hospital.servicesprovided.dto.ServicesProvidedResponseDTO;
import com.hospital.servicesprovided.model.ServicesProvided;
import com.hospital.servicesprovided.repository.ServicesProvidedRepository;
import org.springframework.stereotype.Service;

@Service
public class ServicesProvidedService {

    private final ServicesProvidedRepository servicesProvidedRepository;
    private final AdmissionService admissionService;
    private final DoctorService doctorService;

    public ServicesProvidedService(ServicesProvidedRepository servicesProvidedRepository, AdmissionService admissionService, DoctorService doctorService) {
        this.servicesProvidedRepository = servicesProvidedRepository;
        this.admissionService = admissionService;
        this.doctorService = doctorService;
    }

    private void validateServiceOrigin(ServicesProvidedRequestDTO request) {
        switch (request.getType()) {
            case DRUG -> {
                if (request.getDrugId() == null) {
                    throw new RuntimeException("Medicamento é obrigatório para o tipo DRUG");
                }
                if (request.getDailyId() != null || request.getExamId() != null) {
                    throw new RuntimeException("Apenas o medicamento deve ser informado para o tipo DRUG");
                }
            }
            case DAILY -> {
                if (request.getDailyId() == null) {
                    throw new RuntimeException("Diaria é obrigatória para tipo Daily");
                }
                if (request.getDrugId() != null || request.getExamId() != null) {
                    throw new RuntimeException("Apenas a Diaria deve ser informada para tipo Daily");
                }
                if (request.getDoctorId() != null) {
                    throw new RuntimeException("Médico não deve ser informado para tipo Daily");
                }
            }
            case EXAM -> {
                if (request.getExamId() == null) {
                    throw new RuntimeException("Exame é obrigatório para tipo Exam");
                }
                if (request.getDrugId() != null || request.getDailyId() != null) {
                    throw new RuntimeException("Apenas Exame deve ser informado para tipo Exam");
                }
            }
        }
    }

    public void getId(Long id) {
        ServicesProvided servicesProvided = servicesProvidedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não existe"));
    }

    public ServicesProvidedResponseDTO create(ServicesProvidedRequestDTO request) {
        Admission admission = admissionService.getById(request.getAdmissionId());
        Doctor doctor = doctorService.getById(request.getDoctorId());
        validateServiceOrigin(request);

        ServicesProvided servicesProvided = new ServicesProvided();
        servicesProvided.setAdmission(admission);
        servicesProvided.setDoctor(doctor);
        servicesProvided.setType(request.getType());
        servicesProvided.setCount(request.getCount());

        ServicesProvided save = servicesProvidedRepository.save(servicesProvided);
        return toResponseDTO(save);
    }
}

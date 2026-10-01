package com.hospital.servicesprovided.service;

import com.hospital.admission.model.Admission;
import com.hospital.admission.service.AdmissionService;
import com.hospital.bed.enums.BedType;
import com.hospital.daily.model.Daily;
import com.hospital.daily.service.DailyService;
import com.hospital.doctor.model.Doctor;
import com.hospital.doctor.service.DoctorService;
import com.hospital.drug.model.Drug;
import com.hospital.drug.service.DrugService;
import com.hospital.exam.model.Exam;
import com.hospital.exam.service.ExamService;
import com.hospital.servicesprovided.dto.ServicesProvidedRequestDTO;
import com.hospital.servicesprovided.dto.ServicesProvidedResponseDTO;
import com.hospital.servicesprovided.enums.ServicesType;
import com.hospital.servicesprovided.model.ServicesProvided;
import com.hospital.servicesprovided.repository.ServicesProvidedRepository;
import com.hospital.ward.enums.Specialty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServicesProvidedService {

    private final ServicesProvidedRepository servicesProvidedRepository;
    private final AdmissionService admissionService;
    private final DoctorService doctorService;
    private final DrugService drugService;
    private final DailyService dailyService;
    private final ExamService examService;

    public ServicesProvidedService(ServicesProvidedRepository servicesProvidedRepository, AdmissionService admissionService, DoctorService doctorService, DrugService drugService, DailyService dailyService, ExamService examService) {
        this.servicesProvidedRepository = servicesProvidedRepository;
        this.admissionService = admissionService;
        this.doctorService = doctorService;
        this.drugService = drugService;
        this.dailyService = dailyService;
        this.examService = examService;
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

    public ServicesProvidedResponseDTO create(ServicesProvidedRequestDTO request) {
        Admission admission = admissionService.getById(request.getAdmissionId());
        Doctor doctor = request.getDoctorId() != null ? doctorService.getById(request.getDoctorId()) : null;
        validateServiceOrigin(request);

        if (doctor != null) {
            admissionService.validateDoctorIsResponsibleForAdmission(admission, doctor);
        }

        ServicesProvided servicesProvided = new ServicesProvided();
        servicesProvided.setAdmission(admission);
        servicesProvided.setDoctor(doctor);
        servicesProvided.setType(request.getType());
        servicesProvided.setCount(request.getCount());

        BigDecimal unitValue = null;
        switch (request.getType()) {
            case DRUG -> {
                Drug drug = drugService.getById(request.getDrugId());
                servicesProvided.setDrug(drug);
                unitValue = drug.getValue();
            }
            case DAILY -> {
                Daily daily = dailyService.getById(request.getDailyId());
                servicesProvided.setDaily(daily);
                unitValue = daily.getValue();
            }
            case EXAM -> {
                Exam exam = examService.getById(request.getExamId());
                servicesProvided.setExam(exam);
                unitValue = exam.getValue();
            }
        }

        BigDecimal totalValue = unitValue.multiply(BigDecimal.valueOf(request.getCount()));

        servicesProvided.setUnitValue(unitValue);
        servicesProvided.setTotalValue(totalValue);

        ServicesProvided save = servicesProvidedRepository.save(servicesProvided);
        return toResponseDTO(save);
    }

    public ServicesProvidedResponseDTO findById(Long id) {
        ServicesProvided servicesProvided = servicesProvidedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não existe"));
        return toResponseDTO(servicesProvided);
    }

    public List<ServicesProvidedResponseDTO> findAll() {
        return servicesProvidedRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void generateDailyCharges() {

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<Admission> activeAdmissions = admissionService.getAllActiveAdmissions();

        for (Admission admission : activeAdmissions) {
            BedType bedType = admission.getBed().getBedType();
            Specialty specialty = admission.getBed().getRoom().getWard().getSpecialty();

            Daily daily = dailyService.consultar(bedType, specialty);

            boolean jaGerouHoje = servicesProvidedRepository.existsByAdmission_IdAndTypeAndCreatedAtBetween(
                    admission.getId(), ServicesType.DAILY, startOfDay, endOfDay);

            if (jaGerouHoje) {
                continue;
            }

            ServicesProvided servicesProvided = new ServicesProvided();
            servicesProvided.setAdmission(admission);
            servicesProvided.setDoctor(null);
            servicesProvided.setType(ServicesType.DAILY);
            servicesProvided.setCount(1);
            servicesProvided.setDaily(daily);

            BigDecimal unitValue = daily.getValue();
            BigDecimal totalValue = unitValue.multiply(BigDecimal.valueOf(1));

            servicesProvided.setUnitValue(unitValue);
            servicesProvided.setTotalValue(totalValue);

            servicesProvidedRepository.save(servicesProvided);
        }
    }

    public ServicesProvidedResponseDTO toResponseDTO(ServicesProvided servicesProvided) {
        return new ServicesProvidedResponseDTO(
                servicesProvided.getId(),
                servicesProvided.getAdmission().getId(),
                servicesProvided.getDoctor() != null ? servicesProvided.getDoctor().getId() : null,
                servicesProvided.getType(),
                servicesProvided.getCount(),
                servicesProvided.getUnitValue(),
                servicesProvided.getTotalValue(),
                servicesProvided.getDrug() != null ? servicesProvided.getDrug().getId() : null,
                servicesProvided.getDaily() != null ? servicesProvided.getDaily().getId() : null,
                servicesProvided.getExam() != null ? servicesProvided.getExam().getId() : null,
                servicesProvided.getCreatedAt()
        );
    }
}

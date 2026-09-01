package com.hospital.examscheduling.service;

import com.hospital.admission.model.Admission;
import com.hospital.admission.service.AdmissionService;
import com.hospital.doctor.model.Doctor;
import com.hospital.doctor.service.DoctorService;
import com.hospital.exam.model.Exam;
import com.hospital.exam.service.ExamService;
import com.hospital.examscheduling.dto.ExamSchedulingRequestDTO;
import com.hospital.examscheduling.dto.ExamSchedulingResponseDTO;
import com.hospital.examscheduling.dto.ExamSchedulingUpdateRequestDTO;
import com.hospital.examscheduling.enums.ExamStatus;
import com.hospital.examscheduling.model.ExamScheduling;
import com.hospital.examscheduling.repository.ExamSchedulingRepository;
import com.hospital.servicesprovided.enums.ServicesType;
import com.hospital.servicesprovided.model.ServicesProvided;
import com.hospital.servicesprovided.repository.ServicesProvidedRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExamSchedulingService {

    private final ExamSchedulingRepository examSchedulingRepository;
    private final AdmissionService admissionService;
    private final DoctorService doctorService;
    private final ExamService examService;
    private final ServicesProvidedRepository servicesProvidedRepository;

    public ExamSchedulingService(ExamSchedulingRepository examSchedulingRepository, AdmissionService admissionService, DoctorService doctorService, ExamService examService, ServicesProvidedRepository servicesProvidedRepository) {
        this.examSchedulingRepository = examSchedulingRepository;
        this.admissionService = admissionService;
        this.doctorService = doctorService;
        this.examService = examService;
        this.servicesProvidedRepository = servicesProvidedRepository;
    }

    public ExamSchedulingResponseDTO create(ExamSchedulingRequestDTO dto) {
        Admission admission = admissionService.getById(dto.getAdmissionId());
        Doctor doctor = doctorService.getById(dto.getDoctorId());
        admissionService.validateAdmissionIsActive(admission.getStatus());

        validatePatientHasNoExamAtDate(admission.getPatient().getId(), dto.getDate());

        admissionService.validateDoctorIsResponsibleForAdmission(admission, doctor);

        ExamScheduling examScheduling = new ExamScheduling();
        examScheduling.setDate(dto.getDate());
        examScheduling.setNameExam(dto.getNameExam());
        examScheduling.setExamType(dto.getExamType());
        examScheduling.setExamStatus(ExamStatus.SCHEDULED);
        examScheduling.setAdmission(admission);
        examScheduling.setDoctor(doctor);

        ExamScheduling salvo = examSchedulingRepository.save(examScheduling);
        return toResponseDTO(salvo);

    }

    public ExamSchedulingResponseDTO findById(Long id) {
        ExamScheduling examScheduling = getExamOrThrow(id);
        return toResponseDTO(examScheduling);
    }

    public List<ExamSchedulingResponseDTO> findAll() {
        return examSchedulingRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ExamSchedulingResponseDTO update(Long id, ExamSchedulingUpdateRequestDTO dto) {
        Admission admission = admissionService.getById(dto.getAdmissionId());
        ExamScheduling examScheduling = getExamOrThrow(id);

        admissionService.validateAdmissionIsActive(admission.getStatus());

        validatePatientHasNoExamAtDate(admission.getPatient().getId(), dto.getDate(), id);

        examScheduling.setDate(dto.getDate());
        examScheduling.setNameExam(dto.getNameExam());
        examScheduling.setExamType(dto.getExamType());
        examScheduling.setAdmission(admission);

        ExamScheduling updated = examSchedulingRepository.save(examScheduling);
        return toResponseDTO(updated);
    }

    public ExamSchedulingResponseDTO realizarExame(Long id) {
        ExamScheduling examScheduling = getExamOrThrow(id);

        if (examScheduling.getExamStatus() == ExamStatus.CARRIED_OUT || examScheduling.getExamStatus() == ExamStatus.CANCELED) {
            throw new RuntimeException("Exame já foi realizado ou cancelado");
        }

        Exam exam = examService.getByType(examScheduling.getExamType());

        examScheduling.setExamStatus(ExamStatus.CARRIED_OUT);
        ExamScheduling examAtualizado = examSchedulingRepository.save(examScheduling);

        ServicesProvided servicesProvided = new ServicesProvided();
        servicesProvided.setAdmission(examScheduling.getAdmission());
        servicesProvided.setDoctor(examScheduling.getDoctor());
        servicesProvided.setType(ServicesType.EXAM);
        servicesProvided.setCount(1);
        servicesProvided.setUnitValue(exam.getValue());
        servicesProvided.setTotalValue(exam.getValue());
        servicesProvided.setExam(exam);
        servicesProvidedRepository.save(servicesProvided);

        return toResponseDTO(examAtualizado);
    }

    public void delete(Long id) {
        ExamScheduling examScheduling = getExamOrThrow(id);
        examSchedulingRepository.deleteById(id);
    }

    public void validatePatientHasNoExamAtDate(Long patientId, LocalDateTime date) {
        if (examSchedulingRepository.existsByAdmission_Patient_IdAndDate(patientId, date)) {
            throw new RuntimeException("Patient já tem um exame nesse horario");
        }
    }

    public void validatePatientHasNoExamAtDate(Long patientId, LocalDateTime date, Long examId) {
        if (examSchedulingRepository.existsByAdmission_Patient_IdAndDateAndIdNot(patientId, date, examId)) {
            throw new RuntimeException("Patient já tem um exame nesse horario");
        }
    }

    public ExamScheduling getExamOrThrow(Long id) {
        return examSchedulingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exame não existe"));
    }

    public ExamSchedulingResponseDTO toResponseDTO(ExamScheduling examScheduling) {
        return new ExamSchedulingResponseDTO(
                examScheduling.getId(),
                examScheduling.getDate(),
                examScheduling.getNameExam(),
                examScheduling.getExamType(),
                examScheduling.getExamStatus(),
                examScheduling.getAdmission().getId(),
                examScheduling.getDoctor().getId()
        );
    }
}

package com.hospital.exam.service;

import com.hospital.admission.model.Admission;
import com.hospital.admission.service.AdmissionService;
import com.hospital.doctor.model.Doctor;
import com.hospital.doctor.service.DoctorService;
import com.hospital.exam.dto.ExamRequestDTO;
import com.hospital.exam.dto.ExamResponseDTO;
import com.hospital.exam.dto.ExamUpdateRequestDTO;
import com.hospital.exam.enums.ExamStatus;
import com.hospital.exam.model.Exam;
import com.hospital.exam.repository.ExamRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final AdmissionService admissionService;
    private final DoctorService doctorService;

    public ExamService(ExamRepository examRepository, AdmissionService admissionService, DoctorService doctorService) {
        this.examRepository = examRepository;
        this.admissionService = admissionService;
        this.doctorService = doctorService;
    }

    public ExamResponseDTO create(ExamRequestDTO dto) {
        Admission admission = admissionService.getById(dto.getAdmissionId());
        Doctor doctor = doctorService.getById(dto.getDoctorId());
        admissionService.validateAdmissionIsActive(admission.getStatus());

        validatePatientHasNoExamAtDate(admission.getPatient().getId(), dto.getDate());

        admissionService.validateDoctorIsResponsibleForAdmission(admission, doctor);

        Exam exam = new Exam();
        exam.setDate(dto.getDate());
        exam.setNameExam(dto.getNameExam());
        exam.setExamType(dto.getExamType());
        exam.setExamStatus(ExamStatus.SCHEDULED);
        exam.setAdmission(admission);
        exam.setDoctor(doctor);

        Exam salvo = examRepository.save(exam);
        return toResponseDTO(salvo);

    }

    public ExamResponseDTO findById(Long id) {
        Exam exam = getExamOrThrow(id);
        return toResponseDTO(exam);
    }

    public List<ExamResponseDTO> findAll() {
        return examRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ExamResponseDTO update(Long id, ExamUpdateRequestDTO dto) {
        Admission admission = admissionService.getById(dto.getAdmissionId());
        Exam exam = getExamOrThrow(id);

        admissionService.validateAdmissionIsActive(admission.getStatus());

        validatePatientHasNoExamAtDate(admission.getPatient().getId(), dto.getDate(), id);

        exam.setDate(dto.getDate());
        exam.setNameExam(dto.getNameExam());
        exam.setExamType(dto.getExamType());
        exam.setAdmission(admission);

        Exam updated = examRepository.save(exam);
        return toResponseDTO(updated);
    }

    public void delete(Long id) {
        Exam exam = getExamOrThrow(id);
        examRepository.deleteById(id);
    }

    public void validatePatientHasNoExamAtDate(Long patientId, LocalDateTime date) {
        if (examRepository.existsByAdmission_Patient_IdAndDate(patientId, date)) {
            throw new RuntimeException("Patient já tem um exame nesse horario");
        }
    }

    public void validatePatientHasNoExamAtDate(Long patientId, LocalDateTime date, Long examId) {
        if (examRepository.existsByAdmission_Patient_IdAndDateAndIdNot(patientId, date, examId)) {
            throw new RuntimeException("Patient já tem um exame nesse horario");
        }
    }

    public Exam getExamOrThrow(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exame não existe"));
    }

    public ExamResponseDTO toResponseDTO(Exam exam) {
        return new ExamResponseDTO(
                exam.getId(),
                exam.getDate(),
                exam.getNameExam(),
                exam.getExamType(),
                exam.getExamStatus(),
                exam.getAdmission().getId(),
                exam.getDoctor().getId()
        );
    }
}

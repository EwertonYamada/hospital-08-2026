package com.hospital.exam.service;

import com.hospital.admission.enums.AdmissionStatus;
import com.hospital.admission.model.Admission;
import com.hospital.admission.service.AdmissionService;
import com.hospital.exam.dto.ExamRequestDTO;
import com.hospital.exam.dto.ExamResponseDTO;
import com.hospital.exam.enums.ExamStatus;
import com.hospital.exam.model.Exam;
import com.hospital.exam.repository.ExamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final AdmissionService admissionService;

    public ExamService(ExamRepository examRepository, AdmissionService admissionService) {
        this.examRepository = examRepository;
        this.admissionService = admissionService;
    }

    public ExamResponseDTO create(ExamRequestDTO dto) {
        Admission admission = admissionService.getById(dto.getAdmissionId());
        if (admission.getStatus() != AdmissionStatus.ACTIVE) {
            throw new RuntimeException("Pacinte não esta hospitalizado");
        }

        if (examRepository.existsByAdmission_Patient_IdAndDate(admission.getPatient().getId(), dto.getDate())) {
            throw new RuntimeException("Patient já tem um exame nesse horario");
        }

        // Finalizar validação de Medico dps do PR do Pedro

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
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exame não existe"));
        return toResponseDTO(exam);
    }

    public List<ExamResponseDTO> findAll() {
        return examRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ExamResponseDTO update(Long id, ExamRequestDTO dto) {
        Admission admission = admissionService.getById(dto.getAdmissionId());
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exame não existe"));

        if (admission.getStatus() != AdmissionStatus.ACTIVE) {
            throw new RuntimeException("Paciente não esta hospitalizado");
        }

        if (examRepository.existsByAdmission_Patient_IdAndDateAndIdNot(admission.getPatient().getId(), dto.getDate(), id)) {
            throw new RuntimeException("Patient já tem um exame nesse horario");
        }

        exam.setDate(dto.getDate());
        exam.setNameExam(dto.getNameExam());
        exam.setExamType(dto.getExamType());
        exam.setAdmission(admission);

        Exam updated = examRepository.save(exam);
        return toResponseDTO(updated);
    }

    public void delete(Long id) {
        examRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Exame não existe"));
        examRepository.deleteById(id);
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

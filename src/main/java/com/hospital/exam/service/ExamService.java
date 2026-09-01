package com.hospital.exam.service;

import com.hospital.exam.dto.ExamRequest;
import com.hospital.exam.dto.ExamUpdateRequest;
import com.hospital.exam.model.Exam;
import com.hospital.exam.repository.ExamRepository;
import com.hospital.examscheduling.enums.ExamType;
import com.hospital.utils.exceptions.AlreadyExistingEntityException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {
    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public Exam criar(ExamRequest request) {
        if (examRepository.existsByType(request.type())) {
            throw  new AlreadyExistingEntityException("Duplicado");
        }
        Exam exam = new Exam();
        exam.setName(request.name());
        exam.setType(request.type());
        exam.setValue(request.value());
        examRepository.save(exam);
        return exam;
    }

    public Exam getById(Long id) {
        return this.examRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id nao encontrado"));
    }

    public List<Exam> findAll() {
        return this.examRepository.findAll();
    }

    public Exam atualizar(Long id, ExamUpdateRequest request) {

        Exam exam = this.getById(id);

        exam.setValue(request.value());
        examRepository.save(exam);
        return exam;
    }

    public void deletar(Long id) {
        Exam exam = this.getById(id);
        this.examRepository.delete(exam);
    }

    public Exam getByType(ExamType type) {
        Exam exam = examRepository.findByType(type).orElseThrow(() -> new EntityNotFoundException("Tipo nao encontrado"));
        return  exam;
    }
}

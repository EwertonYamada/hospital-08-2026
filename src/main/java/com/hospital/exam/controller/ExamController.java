package com.hospital.exam.controller;

import com.hospital.exam.dto.ExamRequestDTO;
import com.hospital.exam.dto.ExamResponseDTO;
import com.hospital.exam.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exams")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    public ResponseEntity<ExamResponseDTO> create(@RequestBody @Valid ExamRequestDTO dto) {
        ExamResponseDTO exam = examService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(exam);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamResponseDTO> findById(@PathVariable Long id) {
        ExamResponseDTO exam = examService.findById(id);
        return ResponseEntity.ok(exam);
    }

    @GetMapping
    public ResponseEntity<List<ExamResponseDTO>> findAll() {
        List<ExamResponseDTO> exams = examService.findAll();
        return ResponseEntity.ok(exams);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ExamRequestDTO dto) {
        ExamResponseDTO exam = examService.update(id, dto);
        return ResponseEntity.ok(exam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        examService.delete(id);
        return ResponseEntity.noContent().build();
    }
}



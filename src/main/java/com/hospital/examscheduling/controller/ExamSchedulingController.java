package com.hospital.examscheduling.controller;

import com.hospital.examscheduling.dto.ExamSchedulingRequestDTO;
import com.hospital.examscheduling.dto.ExamSchedulingResponseDTO;
import com.hospital.examscheduling.dto.ExamSchedulingUpdateRequestDTO;
import com.hospital.examscheduling.service.ExamSchedulingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exams")
public class ExamSchedulingController {

    private final ExamSchedulingService examService;

    public ExamSchedulingController(ExamSchedulingService examService) {
        this.examService = examService;
    }

    @PostMapping
    public ResponseEntity<ExamSchedulingResponseDTO> create(@RequestBody @Valid ExamSchedulingRequestDTO dto) {
        ExamSchedulingResponseDTO exam = examService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(exam);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamSchedulingResponseDTO> findById(@PathVariable Long id) {
        ExamSchedulingResponseDTO exam = examService.findById(id);
        return ResponseEntity.ok(exam);
    }

    @GetMapping
    public ResponseEntity<List<ExamSchedulingResponseDTO>> findAll() {
        List<ExamSchedulingResponseDTO> exams = examService.findAll();
        return ResponseEntity.ok(exams);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamSchedulingResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ExamSchedulingUpdateRequestDTO dto) {
        ExamSchedulingResponseDTO exam = examService.update(id, dto);
        return ResponseEntity.ok(exam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        examService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/realizar")
    public ResponseEntity<ExamSchedulingResponseDTO> realizar(@PathVariable Long id) {
        ExamSchedulingResponseDTO  exam = examService.realizarExame(id);
        return ResponseEntity.ok(exam);
    }
}



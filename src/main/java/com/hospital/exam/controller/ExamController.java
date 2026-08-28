package com.hospital.exam.controller;

import com.hospital.exam.dto.ExamRequest;
import com.hospital.exam.dto.ExamUpdateRequest;
import com.hospital.exam.model.Exam;
import com.hospital.exam.service.ExamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam")
public class ExamController {
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    public ResponseEntity<Exam> criar(@RequestBody ExamRequest request){

        return ResponseEntity.status(HttpStatus.CREATED).body(examService.criar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> getById(@PathVariable Long id){

        return ResponseEntity.status(HttpStatus.OK).body(examService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Exam>> findAll(){

        return ResponseEntity.status(HttpStatus.OK).body(examService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exam> atualizar(@PathVariable Long id, @RequestBody ExamUpdateRequest request){

        return ResponseEntity.status(HttpStatus.OK).body(examService.atualizar(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        this.examService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

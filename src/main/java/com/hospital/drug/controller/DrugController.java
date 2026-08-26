package com.hospital.drug.controller;

import com.hospital.drug.dto.DrugRequest;
import com.hospital.drug.model.Drug;
import com.hospital.drug.service.DrugService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drug")
public class DrugController {
    private final DrugService drugService;

    public DrugController(DrugService drugService) {
        this.drugService = drugService;
    }

    @PostMapping
    public ResponseEntity<Drug> criar(@RequestBody DrugRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(drugService.criar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drug> getById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(drugService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Drug>> listar() {

        return ResponseEntity.status(HttpStatus.OK).body(drugService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Drug> atualizar(@PathVariable Long id, @RequestBody DrugRequest request) {

        return ResponseEntity.status(HttpStatus.OK).body(drugService.atualizar(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        this.drugService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

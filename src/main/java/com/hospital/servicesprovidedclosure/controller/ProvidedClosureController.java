package com.hospital.servicesprovidedclosure.controller;

import com.hospital.servicesprovidedclosure.model.ServicesProvidedClosure;
import com.hospital.servicesprovidedclosure.service.ProvidedClosureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/closure")
public class ProvidedClosureController {

    private final ProvidedClosureService providedClosureService;

    public ProvidedClosureController(ProvidedClosureService providedClosureService) {
        this.providedClosureService = providedClosureService;
    }

    @PostMapping("/{admissionId}")
    public ResponseEntity<ServicesProvidedClosure> fechar(@PathVariable Long admissionId) {

        return ResponseEntity.status(HttpStatus.CREATED).body(this.providedClosureService.fechar(admissionId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicesProvidedClosure> getById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(this.providedClosureService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ServicesProvidedClosure>> findAll() {

        return ResponseEntity.status(HttpStatus.OK).body(this.providedClosureService.findAll());
    }
}

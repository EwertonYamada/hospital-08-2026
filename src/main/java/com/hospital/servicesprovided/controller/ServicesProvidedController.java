package com.hospital.servicesprovided.controller;

import com.hospital.servicesprovided.dto.ServicesProvidedRequestDTO;
import com.hospital.servicesprovided.dto.ServicesProvidedResponseDTO;
import com.hospital.servicesprovided.service.ServicesProvidedService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicesprovided")
public class ServicesProvidedController {

    private final ServicesProvidedService servicesProvidedService;

    public ServicesProvidedController(ServicesProvidedService servicesProvidedService) {
        this.servicesProvidedService = servicesProvidedService;
    }

    @PostMapping
    public ResponseEntity<ServicesProvidedResponseDTO> create(@RequestBody @Valid ServicesProvidedRequestDTO request) {
        ServicesProvidedResponseDTO servicesProvidedResponseDTO = servicesProvidedService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(servicesProvidedResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicesProvidedResponseDTO> findById(@PathVariable Long id) {
        ServicesProvidedResponseDTO servicesProvidedResponseDTO = servicesProvidedService.findById(id);
        return ResponseEntity.ok(servicesProvidedResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ServicesProvidedResponseDTO>> findAll() {
        List<ServicesProvidedResponseDTO> servicesProvidedResponseDTOS = servicesProvidedService.findAll();
        return ResponseEntity.ok(servicesProvidedResponseDTOS);
    }
}

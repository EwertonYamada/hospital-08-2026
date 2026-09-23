package com.hospital.logVisits.controller;

import com.hospital.logVisits.dto.LogVisitsRequestDTO;
import com.hospital.logVisits.dto.LogVisitsResponseDTO;
import com.hospital.logVisits.service.LogVisitsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logVisits")
public class LogVisitsController {

    private final LogVisitsService logVisitsService;

    public LogVisitsController(LogVisitsService logVisitsService) {
        this.logVisitsService = logVisitsService;
    }

    @PostMapping
    public ResponseEntity<LogVisitsResponseDTO> create(@RequestBody @Valid LogVisitsRequestDTO dto) {
        LogVisitsResponseDTO logVisitsResponseDTO = logVisitsService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(logVisitsResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogVisitsResponseDTO> findById(@PathVariable Long id) {
        LogVisitsResponseDTO logVisitsResponseDTO = logVisitsService.findById(id);
        return ResponseEntity.ok(logVisitsResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<LogVisitsResponseDTO>> findAll() {
        List<LogVisitsResponseDTO> logVisitsResponseDTOS = logVisitsService.findAll();
        return ResponseEntity.ok(logVisitsResponseDTOS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LogVisitsResponseDTO> update(@PathVariable Long id, @RequestBody @Valid LogVisitsRequestDTO dto) {
        LogVisitsResponseDTO logVisitsResponseDTO = logVisitsService.update(id, dto);
        return ResponseEntity.ok(logVisitsResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logVisitsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/checkout")
    public ResponseEntity<LogVisitsResponseDTO> checkout(@PathVariable Long id) {
        LogVisitsResponseDTO logVisitsResponseDTO = logVisitsService.checkout(id);
        return ResponseEntity.ok(logVisitsResponseDTO);
    }
}

package com.hospital.logVisits.service;

import com.hospital.admission.enums.AdmissionStatus;
import com.hospital.admission.model.Admission;
import com.hospital.admission.service.AdmissionService;
import com.hospital.logVisits.dto.LogVisitsRequestDTO;
import com.hospital.logVisits.dto.LogVisitsResponseDTO;
import com.hospital.logVisits.model.LogVisits;
import com.hospital.logVisits.repository.LogVisitsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogVisitsService {

    private final LogVisitsRepository logVisitsRepository;
    private final AdmissionService admissionService;

    public LogVisitsService(LogVisitsRepository logVisitsRepository, AdmissionService admissionService) {
        this.logVisitsRepository = logVisitsRepository;
        this.admissionService = admissionService;
    }

    public LogVisitsResponseDTO create(LogVisitsRequestDTO dto) {
        Admission admission = admissionService.getById(dto.getAdmissionId());
        if (admission.getStatus() != AdmissionStatus.ACTIVE) {
            throw new RuntimeException("Internação esta inativa");
        }

        if (logVisitsRepository.existsByAdmission_IdAndDateTimeOutIsNull(dto.getAdmissionId())) {
            throw new RuntimeException("Já existe um visitante nessa internação no momento");
        }

        LogVisits logVisits = new LogVisits();
        logVisits.setName(dto.getName());
        logVisits.setDocument(dto.getDocument());
        logVisits.setDateTimeIn(LocalDateTime.now());
        logVisits.setAdmission(admission);

        LogVisits salvo = logVisitsRepository.save(logVisits);
        return toResponseDTO(salvo);
    }

    public LogVisitsResponseDTO findById(Long id) {
        LogVisits logVisits = logVisitsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visita não existe"));
        return toResponseDTO(logVisits);
    }

    public List<LogVisitsResponseDTO> findAll() {
        return logVisitsRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public  LogVisitsResponseDTO update(Long id, LogVisitsRequestDTO dto) {
        LogVisits logVisits = logVisitsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visita não existe"));

        logVisits.setName(dto.getName());
        logVisits.setDocument(dto.getDocument());


        LogVisits updated = logVisitsRepository.save(logVisits);
        return toResponseDTO(updated);
    }

    public void delete(Long id) {
        logVisitsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visita não existe"));
        logVisitsRepository.deleteById(id);
    }

    public LogVisitsResponseDTO checkout(Long id) {
        LogVisits logVisits = logVisitsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visita não existe"));

        if (logVisits.getDateTimeOut() != null) {
            throw new RuntimeException("Visita já foi encerrada");
        }

        logVisits.setDateTimeOut(LocalDateTime.now());
        LogVisits updated = logVisitsRepository.save(logVisits);
        return toResponseDTO(updated);
    }

    public LogVisitsResponseDTO toResponseDTO(LogVisits logVisits) {
        return new LogVisitsResponseDTO(
                logVisits.getId(),
                logVisits.getName(),
                logVisits.getDocument(),
                logVisits.getDateTimeIn(),
                logVisits.getDateTimeOut(),
                logVisits.getAdmission().getId()
        );
    }
}

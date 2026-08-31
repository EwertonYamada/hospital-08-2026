package com.hospital.servicesprovidedclosure.service;

import com.hospital.admission.enums.AdmissionStatus;
import com.hospital.admission.model.Admission;
import com.hospital.admission.service.AdmissionService;
import com.hospital.servicesprovidedclosure.enums.ClosureStatus;
import com.hospital.servicesprovidedclosure.model.ServicesProvidedClosure;
import com.hospital.servicesprovidedclosure.repository.ServicesProvidedClosureRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProvidedClosureService {

    private final ServicesProvidedClosureRepository servicesProvidedClosureRepository;
    private final AdmissionService admissionService;

    public ProvidedClosureService(ServicesProvidedClosureRepository servicesProvidedClosureRepository, AdmissionService admissionService) {
        this.servicesProvidedClosureRepository = servicesProvidedClosureRepository;
        this.admissionService = admissionService;
    }

    public ServicesProvidedClosure fechar(Long admissionId) {
        Admission admission = admissionService.getById(admissionId);

        if (servicesProvidedClosureRepository.existsByAdmissionId(admissionId)) {
            throw new RuntimeException("Internacao ja foi fechada");
        }

        if (admission.getStatus() != AdmissionStatus.INACTIVE) {
            throw new RuntimeException("Internacao ainda nao recebeu alta");
        }

        BigDecimal subtotal = BigDecimal.ZERO; // TODO: substituir pela soma real das despesas quando o Requisito 10 (ServicesProvided) do Carlos existir
        BigDecimal coverageRate = BigDecimal.ZERO; // TODO: substituir pelo coverage_rate real do convênio quando existir o vínculo paciente-convênio

        BigDecimal discount = subtotal.multiply(coverageRate);
        BigDecimal total = subtotal.subtract(discount);

        ServicesProvidedClosure servicesProvidedClosure = new ServicesProvidedClosure();
        servicesProvidedClosure.setAdmission(admission);
        servicesProvidedClosure.setSubtotal(subtotal);
        servicesProvidedClosure.setDiscount(discount);
        servicesProvidedClosure.setTotal(total);
        servicesProvidedClosure.setInsuranceCoverageRate(coverageRate);
        servicesProvidedClosure.setCreatedAt(LocalDateTime.now());
        servicesProvidedClosure.setStatus(ClosureStatus.OPEN);

        servicesProvidedClosureRepository.save(servicesProvidedClosure);
        return servicesProvidedClosure;
    }

    public ServicesProvidedClosure getById(Long id) {
        return servicesProvidedClosureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fechamento com o id " + id + " nao encontrado"));
    }

    public List<ServicesProvidedClosure> findAll() {
        return servicesProvidedClosureRepository.findAll();
    }
}
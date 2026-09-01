package com.hospital.servicesprovidedclosure.service;

import com.hospital.admission.enums.AdmissionStatus;
import com.hospital.admission.model.Admission;
import com.hospital.admission.service.AdmissionService;
import com.hospital.medicalInsurance.model.MedicalInsurance;
import com.hospital.patient.model.Patient;
import com.hospital.servicesprovided.enums.ServicesType;
import com.hospital.servicesprovided.model.ServicesProvided;
import com.hospital.servicesprovided.repository.ServicesProvidedRepository;
import com.hospital.servicesprovidedclosure.dto.FinancialReportResponseDTO;
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
    private final ServicesProvidedRepository servicesProvidedRepository;

    public ProvidedClosureService(ServicesProvidedClosureRepository servicesProvidedClosureRepository, AdmissionService admissionService, ServicesProvidedRepository servicesProvidedRepository) {
        this.servicesProvidedClosureRepository = servicesProvidedClosureRepository;
        this.admissionService = admissionService;
        this.servicesProvidedRepository = servicesProvidedRepository;
    }

    public ServicesProvidedClosure fechar(Long admissionId) {
        Admission admission = admissionService.getById(admissionId);

        if (servicesProvidedClosureRepository.existsByAdmissionId(admissionId)) {
            throw new RuntimeException("Internacao ja foi fechada");
        }

        if (admission.getStatus() != AdmissionStatus.INACTIVE) {
            throw new RuntimeException("Internacao ainda nao recebeu alta");
        }

        Patient patient = admission.getPatient();
        MedicalInsurance medicalInsurance = patient.getMedicalInsurance();

        if (medicalInsurance == null) {
            throw new RuntimeException("Paciente nao possui convenio");
        }

        BigDecimal coverageRate = BigDecimal.valueOf(medicalInsurance.getCoverageRate());

        List<ServicesProvided> despesas = servicesProvidedRepository.findByAdmission_Id(admissionId);

        BigDecimal subtotal = despesas.stream()
                .map(ServicesProvided::getTotalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

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

    public FinancialReportResponseDTO gerarRelatorio(Long closureId) {
        ServicesProvidedClosure closure = this.getById(closureId);
        Long admissionId = closure.getAdmission().getId();
        List<ServicesProvided> despesas = servicesProvidedRepository.findByAdmission_Id(admissionId);

        List<ServicesProvided> listaDiarias = despesas.stream()
                .filter(d -> d.getType() == ServicesType.DAILY)
                .toList();
        List<ServicesProvided> listaMedicamentos = despesas.stream()
                .filter(d -> d.getType() == ServicesType.DRUG)
                .toList();
        List<ServicesProvided> listaExames = despesas.stream()
                .filter(d -> d.getType() == ServicesType.EXAM)
                .toList();

        Integer numeroDiarias = listaDiarias.size();
        BigDecimal valorDiarias = listaDiarias.stream()
                .map(ServicesProvided::getTotalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal valorMedicamentos = listaMedicamentos.stream()
                .map(ServicesProvided::getTotalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal valorExame = listaExames.stream()
                .map(ServicesProvided::getTotalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new FinancialReportResponseDTO(
                numeroDiarias,
                valorDiarias,
                valorMedicamentos,
                valorExame,
                closure.getSubtotal(),
                closure.getDiscount(),
                closure.getTotal()
        );
    }

    public List<ServicesProvidedClosure> findAll() {
        return servicesProvidedClosureRepository.findAll();
    }

}
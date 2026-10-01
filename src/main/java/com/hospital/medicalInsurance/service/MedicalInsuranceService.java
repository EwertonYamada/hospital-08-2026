package com.hospital.medicalInsurance.service;

import com.hospital.medicalInsurance.dto.MedicalInsuranceRequestDTO;
import com.hospital.medicalInsurance.dto.MedicalInsuranceResponseDTO;
import com.hospital.medicalInsurance.model.MedicalInsurance;
import com.hospital.medicalInsurance.repository.MedicalInsuranceRepository;
import com.hospital.patient.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalInsuranceService {

    private final MedicalInsuranceRepository medicalInsuranceRepository;
    private final PatientService patientService;

    public MedicalInsuranceService(MedicalInsuranceRepository medicalInsuranceRepository, PatientService patientService) {
        this.medicalInsuranceRepository = medicalInsuranceRepository;
        this.patientService = patientService;
    }

    private void validateMedicalInsuranceExists(MedicalInsuranceRequestDTO dto) {
        if (medicalInsuranceRepository.existsByType(dto.getType())) {
            throw new RuntimeException("Já existe um convênio cadastrado desse tipo");
        }
    }

    public MedicalInsuranceResponseDTO create(MedicalInsuranceRequestDTO dto) {
        validateMedicalInsuranceExists(dto);

        MedicalInsurance medicalInsurance = new MedicalInsurance();
        medicalInsurance.setType(dto.getType());
        medicalInsurance.setCoverageRate(dto.getCoverageRate());

        MedicalInsurance save = medicalInsuranceRepository.save(medicalInsurance);
        return toResponseDTO(save);
    }

    public MedicalInsuranceResponseDTO findById(Long id) {
        MedicalInsurance medicalInsurance = getById(id);
        return toResponseDTO(medicalInsurance);
    }

    public MedicalInsurance getById(Long id) {
        return medicalInsuranceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Convênio não existe"));
    }

    public List<MedicalInsuranceResponseDTO> findAll() {
        return medicalInsuranceRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public MedicalInsuranceResponseDTO update(Long id, MedicalInsuranceRequestDTO dto) {
        MedicalInsurance medicalInsurance = getById(id);

        if (medicalInsuranceRepository.existsByTypeAndIdNot(dto.getType(), id)) {
            throw new RuntimeException("Já existe um convênio cadastrado desse tipo");
        }

        medicalInsurance.setType(dto.getType());
        medicalInsurance.setCoverageRate(dto.getCoverageRate());

        MedicalInsurance updated = medicalInsuranceRepository.save(medicalInsurance);
        return toResponseDTO(updated);
    }

    public void delete(Long id) {
        getById(id);

        if (patientService.existsPatientWithMedicalInsurance(id)) {
            throw new RuntimeException("Impossivel de Deletar, existe Pacientes vinculados a este Convênio");
        }

        medicalInsuranceRepository.deleteById(id);
    }

    public MedicalInsuranceResponseDTO toResponseDTO(MedicalInsurance medicalInsurance) {
        return new MedicalInsuranceResponseDTO(
                medicalInsurance.getId(),
                medicalInsurance.getType(),
                medicalInsurance.getCoverageRate()
        );
    }
}
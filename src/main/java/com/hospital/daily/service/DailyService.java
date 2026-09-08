package com.hospital.daily.service;

import com.hospital.bed.enums.BedType;
import com.hospital.daily.dto.DailyRequest;
import com.hospital.daily.model.Daily;
import com.hospital.daily.repository.DailyRepository;
import com.hospital.utils.exceptions.AlreadyExistingEntityException;
import com.hospital.ward.enums.Specialty;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DailyService {
    private final DailyRepository dailyRepository;

    public DailyService(DailyRepository dailyRepository) {
        this.dailyRepository = dailyRepository;
    }

    public void validarDuplicidade(BedType bedType, Specialty specialty) {

        if (dailyRepository.existsByTypeAndSpecialty(bedType, specialty)) {
            throw new AlreadyExistingEntityException("duplicado");
        }
    }

    public Daily criar(DailyRequest request) {

        this.validarDuplicidade(request.type(), request.specialty());

        Daily daily = new Daily();
        daily.setType(request.type());
        daily.setSpecialty(request.specialty());
        daily.setValue(request.value());
        return dailyRepository.save(daily);
    }

    public Daily getById(Long id) {
        return dailyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("id nao encontrado"));
    }

    public Daily consultar(BedType type, Specialty specialty) {
        return dailyRepository.findByTypeAndSpecialty(type, specialty).orElseThrow(() -> new EntityNotFoundException("nao foi possivel consultar"));
    }

    public Daily atualizar(Long id, DailyRequest dailyRequest) {

        Daily daily = this.getById(id);

        if (!daily.getType().equals(dailyRequest.type()) || !daily.getSpecialty().equals(dailyRequest.specialty())) {
            this.validarDuplicidade(dailyRequest.type(), dailyRequest.specialty());
        }
            daily.setType(dailyRequest.type());
            daily.setSpecialty(dailyRequest.specialty());
            daily.setValue(dailyRequest.value());

        return dailyRepository.save(daily);
    }

    public void deletar(Long id) {
        Daily daily = this.getById(id);
        dailyRepository.delete(daily);
    }
}

package com.hospital.doctor.service;

import com.hospital.doctor.dto.DoctorRequest;
import com.hospital.doctor.model.Doctor;
import com.hospital.doctor.repository.DoctorRepository;
import com.hospital.utils.exceptions.AlreadyExistingEntityException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }
    public Doctor cadastrar(DoctorRequest doctorRequest){

        this.validarCrmDuplicado(doctorRequest.crm());
        Doctor doctor = new Doctor();
        doctor.setName(doctorRequest.name());
        doctor.setCrm(doctorRequest.crm());
        doctor.setSpecialty(doctorRequest.specialty());
        return doctorRepository.save(doctor);
    }

    public Doctor getById(Long doctorId){

      return doctorRepository.findById(doctorId).
              orElseThrow(() -> new EntityNotFoundException("Medico com o Id " + doctorId+ " Nao encontrado"));
    }

    public List<Doctor> findAll(){
        return doctorRepository.findAll();
    }

    private void validarCrmDuplicado(String crm){
        if (doctorRepository.existsByCrm(crm)) {
            throw new AlreadyExistingEntityException("CRM ja cadastrado");
        }
    }
}

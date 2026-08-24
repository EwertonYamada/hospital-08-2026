package com.hospital.doctor.controller;

import com.hospital.doctor.dto.DoctorRequest;
import com.hospital.doctor.model.Doctor;
import com.hospital.doctor.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<Doctor> cadastrar(@RequestBody DoctorRequest doctorRequest){
        Doctor doctor = this.doctorService.cadastrar(doctorRequest);
        return ResponseEntity.ok(doctor);
    }
}

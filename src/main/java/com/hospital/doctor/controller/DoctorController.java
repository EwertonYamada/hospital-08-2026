package com.hospital.doctor.controller;

import com.hospital.doctor.dto.DoctorRequest;
import com.hospital.doctor.model.Doctor;
import com.hospital.doctor.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(doctor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getById(@PathVariable Long id){
        Doctor doctor = doctorService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(doctor);
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> findAll(){
        List<Doctor> doctors = doctorService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(doctors);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Doctor> deactivateDoctor(@PathVariable Long id) {
        Doctor doctor = doctorService.deactivateDoctor(id);
        return ResponseEntity.status(HttpStatus.OK).body(doctor);
    }
}

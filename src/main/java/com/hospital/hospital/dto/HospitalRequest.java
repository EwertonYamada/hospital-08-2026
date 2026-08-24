package com.hospital.hospital.dto;

import com.hospital.ward.dto.WardRequest;
import com.hospital.ward.enums.Specialty;

import java.util.List;

public record HospitalRequest(
   String name,
   String cnpj,
   String phoneNumber,
   List<WardRequest> specialties
) {}

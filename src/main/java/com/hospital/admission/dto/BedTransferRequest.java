package com.hospital.admission.dto;

public record BedTransferRequest(
        Long newBedId,
        Long doctorId
) {
}

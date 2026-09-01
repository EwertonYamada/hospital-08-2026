package com.hospital.servicesprovided.dto;

import com.hospital.servicesprovided.enums.ServicesType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicesProvidedRequestDTO {

    @NotNull(message = "Internação é obrigatório")
    private Long admissionId;

    private Long doctorId;

    @NotNull(message = "Tipo é obrigatório")
    private ServicesType type;

    @NotNull(message = "Quantidade é obrigatório")
    private Integer count;

    private Long drugId;

    private Long dailyId;

    private Long examId;

}

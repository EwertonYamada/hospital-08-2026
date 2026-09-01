package com.hospital.payment.dto;

import com.hospital.payment.enums.PaymentMethod;
import com.hospital.payment.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {

    private Long id;
    private Long closureId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private LocalDateTime paidAt;
}

package com.hospital.payment.dto;

import com.hospital.payment.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {

    @NotNull(message = "Fechamento é obrigatório")
    private Long closureId;

    @NotNull(message = "Quantidade é obrigatório")
    private BigDecimal amount;

    @NotNull(message = "Metodo de pagamento é obrigatório")
    private PaymentMethod paymentMethod;
}

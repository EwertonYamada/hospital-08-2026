package com.hospital.payment.service;

import com.hospital.payment.dto.PaymentRequestDTO;
import com.hospital.payment.dto.PaymentResponseDTO;
import com.hospital.payment.enums.PaymentStatus;
import com.hospital.payment.model.Payment;
import com.hospital.payment.repository.PaymentRepository;
import com.hospital.servicesprovidedclosure.enums.ClosureStatus;
import com.hospital.servicesprovidedclosure.model.ServicesProvidedClosure;
import com.hospital.servicesprovidedclosure.service.ProvidedClosureService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ProvidedClosureService providedClosureService;

    public PaymentService(PaymentRepository paymentRepository, ProvidedClosureService providedClosureService) {
        this.paymentRepository = paymentRepository;
        this.providedClosureService = providedClosureService;
    }

    public Payment getById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não existe"));
    }

    public void validateIsPaidOrNot(PaymentStatus paymentStatus) {

        if (paymentStatus != PaymentStatus.PENDING) {
            throw new RuntimeException("Pagamento já foi confirmado");
        }
    }

    public PaymentResponseDTO create(PaymentRequestDTO request) {
        ServicesProvidedClosure servicesProvidedClosure = providedClosureService.getById(request.getClosureId());

        providedClosureService.validateClosureIsNotPaid(servicesProvidedClosure.getStatus());

        Payment payment = new Payment();
        payment.setServicesProvidedClosure(servicesProvidedClosure);
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.PENDING);

        Payment save = paymentRepository.save(payment);
        return toResponseDTO(save);
    }

    public PaymentResponseDTO findById(Long id) {
        Payment payment = getById(id);
        return toResponseDTO(payment);
    }

    public List<PaymentResponseDTO> findAll() {
        return paymentRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public PaymentResponseDTO confirm(Long id) {
        Payment payment = getById(id);
        validateIsPaidOrNot(payment.getPaymentStatus());

        payment.setPaymentStatus(PaymentStatus.PAID);
        payment.setPaidAt(LocalDateTime.now());
        Payment confirmed = paymentRepository.save(payment);

        ServicesProvidedClosure closure = confirmed.getServicesProvidedClosure();

        List<Payment> paidPayments = paymentRepository.findByServicesProvidedClosure_IdAndPaymentStatus(closure.getId(), PaymentStatus.PAID);

        BigDecimal totalPago = paidPayments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalPago.compareTo(closure.getTotal()) >= 0) {
            providedClosureService.markAsPaid(closure.getId());
        }

        return toResponseDTO(confirmed);
    }


    public PaymentResponseDTO toResponseDTO(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getServicesProvidedClosure().getId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getPaidAt()
        );
    }
}

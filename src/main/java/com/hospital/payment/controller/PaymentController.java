package com.hospital.payment.controller;

import com.hospital.payment.dto.PaymentRequestDTO;
import com.hospital.payment.dto.PaymentResponseDTO;
import com.hospital.payment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @PostMapping
    public ResponseEntity<PaymentResponseDTO> create(@RequestBody @Valid PaymentRequestDTO request) {
        PaymentResponseDTO paymentResponseDTO = paymentService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> findById(@PathVariable Long id) {
        PaymentResponseDTO paymentResponseDTO = paymentService.findById(id);
        return ResponseEntity.ok(paymentResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> findAll() {
        List<PaymentResponseDTO> paymentResponseDTO = paymentService.findAll();
        return ResponseEntity.ok(paymentResponseDTO);
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<PaymentResponseDTO> confirm(@PathVariable Long id) {
        PaymentResponseDTO paymentResponseDTO = paymentService.confirm(id);
        return ResponseEntity.ok(paymentResponseDTO);
    }
}

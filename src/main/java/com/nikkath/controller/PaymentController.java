package com.nikkath.controller;

import com.nikkath.dto.PaymentRequestDTO;
import com.nikkath.dto.PaymentResponseDTO;
import com.nikkath.model.DiningSession;
import com.nikkath.service.DiningSessionService;
import com.nikkath.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    private final DiningSessionService diningSessionService;

    @PostMapping("/pay/{billId}")
    public ResponseEntity<PaymentResponseDTO> payBill(
            @PathVariable Long billId,
            @RequestBody PaymentRequestDTO requestDTO) {

        return ResponseEntity.ok(
                paymentService.payBill(billId, requestDTO));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(
            @PathVariable Long paymentId) {

        return ResponseEntity.ok(
                paymentService.getPaymentById(paymentId));
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments() {

        return ResponseEntity.ok(
                paymentService.getAllPayments());
    }

    @PutMapping("/close/{sessionId}")
    public ResponseEntity<DiningSession> closeSession(@PathVariable Long sessionId) {

        return ResponseEntity.ok(paymentService.closeSession(sessionId));
    }
}

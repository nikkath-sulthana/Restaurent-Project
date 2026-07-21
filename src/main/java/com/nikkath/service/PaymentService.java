package com.nikkath.service;

import com.nikkath.dto.PaymentRequestDTO;
import com.nikkath.dto.PaymentResponseDTO;
import com.nikkath.exception.BadRequestException;
import com.nikkath.exception.ResourceNotFoundException;
import com.nikkath.model.*;
import com.nikkath.repository.BillRepository;
import com.nikkath.repository.DiningSessionRepository;
import com.nikkath.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentService {

    private final BillRepository billRepository;
    private final PaymentRepository paymentRepository;
    private final DiningSessionRepository diningSessionRepository;


    public PaymentResponseDTO payBill(Long billId, PaymentRequestDTO requestDTO) {

        // Find bill
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bill Not Found"));


        // Check duplicate payment
        if (paymentRepository.findByBill(bill).isPresent()) {
            throw new BadRequestException("Payment has already been made for this bill.");
        }


        // Create payment
        Payment payment = new Payment();

        payment.setBill(bill);
        payment.setAmount(bill.getTotalAmount());
        payment.setPaymentMethod(requestDTO.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setPaidAt(LocalDateTime.now());


        // Generate receipt number
        payment.setReceiptNumber(
                "RCPT-" + UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase()
        );


        // Generate transaction id for online payments
        if (requestDTO.getPaymentMethod() == PaymentMethod.CARD ||
                requestDTO.getPaymentMethod() == PaymentMethod.UPI) {

            payment.setTransactionId(
                    "TXN-" + UUID.randomUUID()
                            .toString()
                            .substring(0, 10)
                            .toUpperCase()
            );

        } else {
            payment.setTransactionId(null);
        }


        // Save payment
        Payment savedPayment = paymentRepository.save(payment);


        // Close dining session after successful payment
        closeSession(bill.getDiningSession().getId());


        return mapToPaymentResponseDTO(savedPayment);
    }



    public DiningSession closeSession(Long sessionId) {

        DiningSession diningSession = diningSessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Dining Session Not Found"));


        // Already closed check
        if (!diningSession.isActive()) {
            throw new BadRequestException("Dining Session is already closed.");
        }


        // Check bill exists
        Bill bill = billRepository.findByDiningSession(diningSession)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bill Not Found"));


        // Check payment completed
        Payment payment = paymentRepository.findByBill(bill)
                .orElseThrow(() ->
                        new BadRequestException("Payment has not been completed."));


        if (payment.getPaymentStatus() != PaymentStatus.SUCCESS) {
            throw new BadRequestException("Payment has not been completed.");
        }


        // Close session
        diningSession.setActive(false);

        return diningSessionRepository.save(diningSession);
    }



    public PaymentResponseDTO getPaymentById(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment Not Found"));

        return mapToPaymentResponseDTO(payment);
    }



    public List<PaymentResponseDTO> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(this::mapToPaymentResponseDTO)
                .toList();
    }



    private PaymentResponseDTO mapToPaymentResponseDTO(Payment payment) {

        PaymentResponseDTO dto = new PaymentResponseDTO();

        dto.setId(payment.getId());
        dto.setBillId(payment.getBill().getId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setPaidAt(payment.getPaidAt());
        dto.setReceiptNumber(payment.getReceiptNumber());
        dto.setTransactionId(payment.getTransactionId());

        return dto;
    }
}
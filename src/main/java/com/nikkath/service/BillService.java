package com.nikkath.service;

import com.nikkath.dto.BillResponseDTO;
import com.nikkath.exception.BadRequestException;
import com.nikkath.exception.ResourceNotFoundException;
import com.nikkath.model.Bill;
import com.nikkath.model.DiningSession;
import com.nikkath.model.Order;
import com.nikkath.model.OrderStatus;
import com.nikkath.repository.BillRepository;
import com.nikkath.repository.DiningSessionRepository;
import com.nikkath.repository.OrderRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BillService {

    private final DiningSessionRepository diningSessionRepository;

    private final BillRepository billRepository;

    private final OrderRepository orderRepository;

    public BillResponseDTO generateBill(Long sessionId) {

        // Find the dining session
        DiningSession diningSession = diningSessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Dining Session Not Found"));

        // Check if a bill already exists
        if (billRepository.findByDiningSession(diningSession).isPresent()) {
            throw new BadRequestException("Bill has already been generated for this session.");
        }

        // Get all served orders
        List<Order> orders = orderRepository.findByDiningSessionAndStatus(
                diningSession, OrderStatus.SERVED);

        if (orders.isEmpty()) {
            throw new BadRequestException("No served orders found for this dining session.");
        }

        // Calculate subtotal
        double subtotal = 0;

        for (Order order : orders) {
            subtotal += order.getTotalAmount();
        }

        // Calculate GST (5%)
        double gst = subtotal * 0.05;

        // Calculate final total
        double totalAmount = subtotal + gst;

        // Create bill
        Bill bill = new Bill();
        bill.setDiningSession(diningSession);
        bill.setSubtotal(subtotal);
        bill.setGst(gst);
        bill.setTotalAmount(totalAmount);
        bill.setGeneratedAt(LocalDateTime.now());

        // Save bill
        Bill savedBill = billRepository.save(bill);

        return mapToBillResponse(savedBill);
    }


    public BillResponseDTO getBillBySession(Long sessionId) {

        DiningSession diningSession = diningSessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Dining Session Not Found"));

        Bill bill = billRepository.findByDiningSession(diningSession)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bill Not Found"));

        return mapToBillResponse(bill);
    }

    public List<BillResponseDTO> getAllBills() {

        List<Bill> bills = billRepository.findAll();

        List<BillResponseDTO> dtoList = new ArrayList<>();

        for (Bill bill : bills) {
            dtoList.add(mapToBillResponse(bill));
        }

        return dtoList;
    }

    //the below code is if i prefer java streams...

//    public List<BillResponseDTO> getAllBills() {
//
//        return billRepository.findAll()
//                .stream()
//                .map(this::mapToBillResponse)
//                .toList();
//    }


    private BillResponseDTO mapToBillResponse(Bill bill) {

        BillResponseDTO dto = new BillResponseDTO();

        dto.setBillId(bill.getId());
        dto.setDiningSessionId(bill.getDiningSession().getId());
        dto.setSubtotal(bill.getSubtotal());
        dto.setGst(bill.getGst());
        dto.setTotalAmount(bill.getTotalAmount());
        dto.setGeneratedAt(bill.getGeneratedAt());

        return dto;
    }
}
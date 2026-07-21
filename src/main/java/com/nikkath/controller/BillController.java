package com.nikkath.controller;

import com.nikkath.dto.BillResponseDTO;
import com.nikkath.service.BillService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/bill")
@AllArgsConstructor
public class BillController {
 private final BillService billService;

    @PostMapping("/generate/{sessionId}")
    public ResponseEntity<BillResponseDTO> generateBill(@PathVariable Long sessionId) {
        return ResponseEntity.ok(billService.generateBill(sessionId));
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<BillResponseDTO> getBillBySession(
            @PathVariable Long sessionId) {

        return ResponseEntity.ok(
                billService.getBillBySession(sessionId));
    }

    @GetMapping
    public ResponseEntity<List<BillResponseDTO>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }

}

package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.dto.TransactionStatus;
import com.eteration.simplebanking.services.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction/v1")
public class TransactionStatusController {

    private final ITransactionService transactionService;

    @Autowired
    public TransactionStatusController(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{approvalCode}")
    public TransactionStatus getTransaction(@PathVariable String approvalCode) throws Exception {
        return ResponseEntity.ok(transactionService.findTransactionByApprovalCode(approvalCode)).getBody();
    }
}

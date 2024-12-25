package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.dto.TransactionStatus;
import com.eteration.simplebanking.entity.Account;
import com.eteration.simplebanking.entity.BillPaymentTransaction;
import com.eteration.simplebanking.entity.DepositTransaction;
import com.eteration.simplebanking.entity.WithdrawalTransaction;
import com.eteration.simplebanking.services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.eteration.simplebanking.constant.ErrorMessage.ACCOUNT_ALREADY_EXISTS;

@RestController
@RequestMapping("/account/v1")
public class AccountController {

    private final IAccountService accountService;

    @Autowired
    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber, @RequestBody DepositTransaction transaction) {
        Account account = accountService.findAccount(accountNumber);
        accountService.processTransaction(account, transaction);
        return ResponseEntity.ok(new TransactionStatus(HttpStatus.OK, transaction.getApprovalCode()));
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(@PathVariable String accountNumber, @RequestBody WithdrawalTransaction transaction) {
        Account account = accountService.findAccount(accountNumber);
        accountService.processTransaction(account, transaction);
        return ResponseEntity.ok(new TransactionStatus(HttpStatus.OK, transaction.getApprovalCode()));
    }

    @PostMapping("/bill/{accountNumber}")
    public ResponseEntity<TransactionStatus> billPayment(@PathVariable String accountNumber, @RequestBody BillPaymentTransaction transaction) {
        Account account = accountService.findAccount(accountNumber);
        accountService.processTransaction(account, transaction);
        return ResponseEntity.ok(new TransactionStatus(HttpStatus.OK, transaction.getApprovalCode()));
    }

    @PostMapping
    public ResponseEntity<Account> addAccount(@RequestBody Account account) {
        if (accountService.findAccountIfExists(account.getAccountNumber()).isPresent()) {
            throw new RuntimeException(ACCOUNT_ALREADY_EXISTS.getMessage() + account.getAccountNumber());
        }
        Account savedAccount = accountService.saveAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        Account account = accountService.findAccount(accountNumber);
        return ResponseEntity.ok(account);
    }
}

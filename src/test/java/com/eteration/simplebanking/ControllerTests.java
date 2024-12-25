package com.eteration.simplebanking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.dto.TransactionStatus;
import com.eteration.simplebanking.entity.*;
import com.eteration.simplebanking.exception.InsufficientBalanceException;

import com.eteration.simplebanking.services.IAccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.Objects;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
class ControllerTests {

    @Spy
    @InjectMocks
    private AccountController controller;

    @Mock
    private IAccountService service;

    @Test
    public void givenId_Credit_thenReturnJson() {
        Account account = new Account("Kerem Karaca", "17892");

        doReturn(account).when(service).findAccount( "17892");
        ResponseEntity<TransactionStatus> result = controller.credit( "17892", new DepositTransaction(1000.0));
        verify(service, times(1)).findAccount("17892");
        assertEquals(HttpStatus.OK, Objects.requireNonNull(result.getBody()).getStatus());
    }

    @Test
    public void givenId_CreditAndThenDebit_thenReturnJson() {
        Account account = new Account("Kerem Karaca", "17892");

        doReturn(account).when(service).findAccount("17892");
        doAnswer(invocation -> {
            Account acc = invocation.getArgument(0);
            Transaction trx = invocation.getArgument(1);
            acc.post(trx);
            return null;
        }).when(service).processTransaction(any(Account.class), any(Transaction.class));

        ResponseEntity<TransactionStatus> result = controller.credit("17892", new DepositTransaction(1000.0));
        ResponseEntity<TransactionStatus> result2  = controller.debit("17892", new WithdrawalTransaction(50.0));

        verify(service, times(2)).findAccount("17892");
        assertEquals(HttpStatus.OK, Objects.requireNonNull(result.getBody()).getStatus());
        assertEquals(HttpStatus.OK, Objects.requireNonNull(result2.getBody()).getStatus());
        assertEquals(950.0, account.getBalance(),0.001);

    }

    @Test
    public void givenId_CreditAndThenDebitMoreGetException_thenReturnJson() {
        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
        Account account = new Account("Kerem Karaca", "17892");

        doReturn(account).when(service).findAccount("17892");
        doAnswer(invocation -> {
            Account acc = invocation.getArgument(0);
            Transaction trx = invocation.getArgument(1);
            acc.post(trx);
            return null;
        }).when(service).processTransaction(any(Account.class), any(Transaction.class));

        ResponseEntity<TransactionStatus> result =  controller.credit("17892", new DepositTransaction(1000.0));
        assertEquals(HttpStatus.OK, Objects.requireNonNull(result.getBody()).getStatus());
        assertEquals(1000.0, account.getBalance(), 0.001);
        verify(service, times(1)).findAccount("17892");
        controller.debit("17892", new WithdrawalTransaction(5000.0));
        });
    }

    @Test
    public void givenAccount_BillPaymentTransaction_thenBalanceUpdatedInController() {
        Account account = new Account("Kerem Karaca", "17892");

        account.credit(1000.0);
        doReturn(account).when(service).findAccount("17892");
        doAnswer(invocation -> {
            Account acc = invocation.getArgument(0);
            Transaction trx = invocation.getArgument(1);
            acc.post(trx);
            return null;
        }).when(service).processTransaction(any(Account.class), any(Transaction.class));

        controller.billPayment("17892", new BillPaymentTransaction(200.0, "Vodafone", "5423345566"));
        verify(service, times(1)).findAccount("17892");
        assertEquals(800.0, account.getBalance(), 0.001);
    }

    @Test
    public void givenId_GetAccount_thenReturnJson() {
        Account account = new Account("Kerem Karaca", "17892");

        doReturn(account).when(service).findAccount("17892");
        ResponseEntity<Account> result = controller.getAccount("17892");
        verify(service, times(1)).findAccount("17892");
        assertEquals(account, result.getBody());
    }
}

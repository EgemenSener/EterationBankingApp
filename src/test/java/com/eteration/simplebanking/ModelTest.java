package com.eteration.simplebanking;

import com.eteration.simplebanking.entity.Account;
import com.eteration.simplebanking.entity.BillPaymentTransaction;
import com.eteration.simplebanking.entity.DepositTransaction;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.entity.WithdrawalTransaction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {
	
	@Test
	public void testCreateAccountAndSetBalance0() {
		Account account = new Account("Kerem Karaca", "17892");
        assertEquals("Kerem Karaca", account.getOwner());
        assertEquals("17892", account.getAccountNumber());
        assertEquals(0, account.getBalance());
	}

	@Test
	public void testDepositIntoBankAccount() {
		Account account = new Account("Demet Demircan", "9834");
		account.credit(100);
        assertEquals(100, account.getBalance());
	}

	@Test
	public void testWithdrawFromBankAccount() throws InsufficientBalanceException {
		Account account = new Account("Demet Demircan", "9834");
		account.credit(100);
        assertEquals(100, account.getBalance());
		account.debit(50);
        assertEquals(50, account.getBalance());
	}

	@Test
	public void testWithdrawException() {
		Assertions.assertThrows( InsufficientBalanceException.class, () -> {
			Account account = new Account("Demet Demircan", "9834");
			account.credit(100);
			account.debit(500);
		  });
	}

	@Test
	public void testBillPaymentTransaction() {
		Account account = new Account("Jim", "12345");
		account.post(new DepositTransaction(1000));
		account.post(new WithdrawalTransaction(200));
		account.post(new BillPaymentTransaction(96.50, "Vodafone", "5423345566"));
		assertEquals( 703.50, account.getBalance(),0.0001);
	}
	
	@Test
	public void testTransactions() throws InsufficientBalanceException {
		Account account = new Account("Canan Kaya", "1234");
        assertEquals(0, account.getTransactions().size());

		DepositTransaction depositTrx = new DepositTransaction(100);
        assertNotNull(depositTrx.getCreatedAt());
		account.post(depositTrx);
        assertEquals(100, account.getBalance());
        assertEquals(1, account.getTransactions().size());

		WithdrawalTransaction withdrawalTrx = new WithdrawalTransaction(60);
        assertNotNull(withdrawalTrx.getCreatedAt());
		account.post(withdrawalTrx);
        assertEquals(40, account.getBalance());
        assertEquals(2, account.getTransactions().size());
	}
}

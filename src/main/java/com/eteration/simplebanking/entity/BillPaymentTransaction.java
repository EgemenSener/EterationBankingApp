package com.eteration.simplebanking.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BILL_PAYMENT")
public class BillPaymentTransaction extends Transaction {
    private String billProvider;
    private String billNumber;

    public BillPaymentTransaction() {}

    public BillPaymentTransaction(double amount, String billProvider, String billNumber) {
        super(amount);
        this.billProvider = billProvider;
        this.billNumber = billNumber;
    }

    //Getters and Setters
    public String getBillProvider() {
        return billProvider;
    }

    public void setBillProvider(String billProvider) {
        this.billProvider = billProvider;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    @Override
    public void apply(Account account) {
        account.debit(this.getAmount());
    }
}

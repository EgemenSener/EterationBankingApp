package com.eteration.simplebanking.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DEPOSIT")
public class DepositTransaction extends Transaction {

    public DepositTransaction() {
        super();
    }

    public DepositTransaction(double amount) {
        super(amount);
    }

    @Override
    public void apply(Account account) {
        account.credit(this.getAmount());
    }
}


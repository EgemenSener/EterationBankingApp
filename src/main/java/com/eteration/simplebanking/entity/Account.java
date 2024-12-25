package com.eteration.simplebanking.entity;

import com.eteration.simplebanking.exception.InsufficientBalanceException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.eteration.simplebanking.constant.ErrorMessage.INSUFFICIENT_BALANCE;
import static com.eteration.simplebanking.constant.ErrorMessage.TRANSACTION_AMOUNT_INVALID;

@Entity
public class Account extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private String owner;

    @Column(nullable = false)
    private double balance = 0.0;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private List<Transaction> transactions = new ArrayList<>();

    public Account() {
    }

    public Account(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
    }

    public void post(Transaction transaction) {
        if (transaction.getAmount() == 0) {
            throw new IllegalArgumentException(TRANSACTION_AMOUNT_INVALID.getMessage());
        }
        transaction.apply(this);
        this.transactions.add(transaction);
    }

    public void credit(double amount) {
        this.balance += amount;
    }

    public void debit(double amount) {
        if (amount > this.balance) {
            throw new InsufficientBalanceException(INSUFFICIENT_BALANCE.getMessage() + amount);
        }
        this.balance -= amount;
    }

    //Getters ve Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() { return transactions; }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}

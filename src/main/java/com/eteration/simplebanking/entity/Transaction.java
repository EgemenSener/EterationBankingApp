package com.eteration.simplebanking.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Transaction extends BaseEntity {

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false, unique = true)
    private String approvalCode;

    @JsonProperty("type")
    public String getType() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }

    public Transaction() {
        this.approvalCode = java.util.UUID.randomUUID().toString();
        this.setCreatedAt(new Date());
    }

    public Transaction(double amount) {
        this();
        this.amount = amount;
    }

    public abstract void apply(Account account);

    //Getters ve Setters
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }
}

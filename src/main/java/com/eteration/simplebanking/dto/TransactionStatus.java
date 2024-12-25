package com.eteration.simplebanking.dto;

import org.springframework.http.HttpStatus;

public class TransactionStatus {

    private HttpStatus status;
    private String approvalCode;

    public TransactionStatus() {
    }

    public TransactionStatus(HttpStatus status, String approvalCode) {
        this.status = status;
        this.approvalCode = approvalCode;
    }

    //Getters ve Setters
    public HttpStatus getStatus() { return status; }

    public void setStatus(HttpStatus status) { this.status = status; }

    public String getApprovalCode() {return approvalCode; }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }
}

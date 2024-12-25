package com.eteration.simplebanking.constant;

public enum ErrorMessage {
    ACCOUNT_NOT_FOUND("Account not found: "),
    ACCOUNT_ALREADY_EXISTS("Account already exists: "),
    TRANSACTION_NOT_FOUND("Transaction not found: "),
    TRANSACTION_CANT_BE_NULl("Transaction can't be null."),
    TRANSACTION_AMOUNT_INVALID("Transaction amount must be greater than zero."),
    INSUFFICIENT_BALANCE("Insufficient balance for this transaction: ");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}


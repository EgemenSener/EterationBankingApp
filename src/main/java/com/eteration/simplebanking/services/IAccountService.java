package com.eteration.simplebanking.services;

import com.eteration.simplebanking.entity.Account;
import com.eteration.simplebanking.entity.Transaction;

import java.util.Optional;

public interface IAccountService {
    Account findAccount(String accountNumber);

    Account saveAccount(Account account);

    Optional<Account>findAccountIfExists(String accountNumber);

    void processTransaction(Account account, Transaction transaction);
}



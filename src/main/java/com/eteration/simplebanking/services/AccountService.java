package com.eteration.simplebanking.services;

import com.eteration.simplebanking.dao.AccountRepository;
import com.eteration.simplebanking.entity.Account;
import com.eteration.simplebanking.entity.Transaction;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.eteration.simplebanking.constant.ErrorMessage.*;

@Service
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findAccount(String accountNumber) {
        return Optional.ofNullable(accountRepository.findByAccountNumber(accountNumber))
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND.getMessage() + accountNumber));
    }

    public Optional<Account> findAccountIfExists(String accountNumber) {
        return Optional.ofNullable(accountRepository.findByAccountNumber(accountNumber));
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public void processTransaction(Account account, Transaction transaction) {
        Optional.ofNullable(account).orElseThrow(() -> {
            assert account != null;
            return new AccountNotFoundException(ACCOUNT_NOT_FOUND.getMessage()
            + account.getAccountNumber());
        });
        Optional.ofNullable(transaction).orElseThrow(() -> new IllegalArgumentException(TRANSACTION_CANT_BE_NULl.getMessage()));

        account.post(transaction);
        accountRepository.save(account);
    }
}

package com.eteration.simplebanking.dao;

import com.eteration.simplebanking.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByApprovalCode(String approvalCode);
}


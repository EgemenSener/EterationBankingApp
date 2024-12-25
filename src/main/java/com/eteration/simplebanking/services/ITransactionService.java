package com.eteration.simplebanking.services;
import com.eteration.simplebanking.dto.TransactionStatus;

public interface ITransactionService {
    TransactionStatus findTransactionByApprovalCode(String approvalCode) throws Exception;
}
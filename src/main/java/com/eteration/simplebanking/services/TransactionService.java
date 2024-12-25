package com.eteration.simplebanking.services;

import com.eteration.simplebanking.dao.TransactionRepository;
import com.eteration.simplebanking.dto.TransactionStatus;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.eteration.simplebanking.constant.ErrorMessage.TRANSACTION_NOT_FOUND;

@Service
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionStatus findTransactionByApprovalCode(String approvalCode) throws Exception {
        return transactionRepository.findByApprovalCode(approvalCode)
                .map(transaction -> {
                    TransactionStatus transactionStatus = new TransactionStatus();
                    transactionStatus.setApprovalCode(approvalCode);
                    transactionStatus.setStatus(HttpStatus.OK);
                    return transactionStatus;
                })
                .orElseThrow(() -> new NotFoundException(TRANSACTION_NOT_FOUND.getMessage() + approvalCode));
    }
}

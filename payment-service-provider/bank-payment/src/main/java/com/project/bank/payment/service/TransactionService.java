package com.project.bank.payment.service;

import com.project.bank.payment.model.Transaction;
import com.project.bank.payment.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction findByPaymentId(String paymentId){
        return transactionRepository.findTransactionByPaymentId(paymentId);
    }

    public void save(Transaction t) {
        transactionRepository.save(t);
    }
}

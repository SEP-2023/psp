package com.project.crypto.repository;

import com.project.crypto.model.CryptoTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends JpaRepository<CryptoTransaction, Long> {
    CryptoTransaction findCryptoTransactionById(Long orderId);
}

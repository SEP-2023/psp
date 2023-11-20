package com.project.paypal.repository;

import com.project.paypal.model.PaypalTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<PaypalTransaction, Long> {
}

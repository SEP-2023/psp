package com.project.bank.payment.repository;

import com.project.bank.payment.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}

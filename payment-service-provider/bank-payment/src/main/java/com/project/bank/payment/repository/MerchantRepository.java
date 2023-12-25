package com.project.bank.payment.repository;

import com.project.bank.payment.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    public Merchant findMerchantByMerchantId(String merchantId);
}

package com.project.bank.payment.service;

import com.project.bank.payment.model.Merchant;
import com.project.bank.payment.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    public Merchant findByMerchantId(String id){
        return merchantRepository.findMerchantByMerchantId(id);
    }
}

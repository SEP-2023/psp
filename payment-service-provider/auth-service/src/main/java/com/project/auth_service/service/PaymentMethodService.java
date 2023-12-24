package com.project.auth_service.service;

import com.project.auth_service.dto.AddPaymentMethodDto;
import com.project.auth_service.dto.PaymentMethodDto;
import com.project.auth_service.model.PaymentMethod;
import com.project.auth_service.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;


    public void addPaymentMethod(PaymentMethod paymentInfo) {
        paymentMethodRepository.save(paymentInfo);
    }

    public List<PaymentMethod> getPaymentMethods(String agencyId) {
        System.out.println(paymentMethodRepository.findAll());
        System.out.println(agencyId);
        List<PaymentMethod> data = paymentMethodRepository.findByAgencyId(agencyId);
        System.out.println(data);
        return data;
    }
}

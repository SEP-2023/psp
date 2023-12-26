package com.project.auth_service.service;

import com.project.auth_service.model.PaymentMethod;
import com.project.auth_service.repository.PaymentMethodRepository;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;
    Dotenv dotenv = Dotenv.load();
    String agencyId = dotenv.get("AGENCY_ID");

    public void addPaymentMethod(PaymentMethod paymentInfo) {
        paymentInfo.setAgencyId(agencyId);
        paymentMethodRepository.save(paymentInfo);
    }

    public void removePaymentMethod(PaymentMethod paymentInfo) {
        System.out.println("usloooooooooooooooooooooooooooooooooooooo");
        paymentInfo.setAgencyId(agencyId);
        System.out.println(paymentInfo.getPaymentMethod());
        List<PaymentMethod> method = paymentMethodRepository.findByAgencyIdAndMethod(dotenv.get("AGENCY_ID"), paymentInfo.getPaymentMethod());
        System.out.println(method);
        paymentMethodRepository.deleteAll(method);
    }

    public List<PaymentMethod> getPaymentMethods(String agencyId) {
        System.out.println(paymentMethodRepository.findAll());
        System.out.println(agencyId);
        List<PaymentMethod> data = paymentMethodRepository.findByAgencyId(agencyId);
        System.out.println(data);
        return data;
    }
}

package com.project.auth_service.controller;

import com.project.auth_service.dto.AddPaymentMethodDto;
import com.project.auth_service.dto.PaymentMethodDto;
import com.project.auth_service.model.PaymentMethod;
import com.project.auth_service.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @GetMapping("/payment-methods/{agencyId}")
    public ResponseEntity<List<PaymentMethodDto>> getShopPaymentMethods(@PathVariable String agencyId){
        System.out.println(agencyId);
        List<PaymentMethod> dtos = paymentMethodService.getPaymentMethods(agencyId);
        List<PaymentMethodDto> methods = new ArrayList<>();
        for (PaymentMethod m: dtos){
            PaymentMethodDto p = new PaymentMethodDto();
            p.setPaymentMethod(m.getPaymentMethod());
            methods.add(p);
        }
        System.out.println(methods);
        return new ResponseEntity<>(methods, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('UPDATE_PAYMENT_METHODS_PERMISSION')")
    @PostMapping("/payment-methods")
    public ResponseEntity<Boolean> addPaymentMethod(@RequestBody AddPaymentMethodDto paymentInfo){
        PaymentMethod method = new PaymentMethod();
        method.setPaymentMethod(paymentInfo.getPaymentMethod());
        method.setAgencyId(paymentInfo.getAgencyId());
        paymentMethodService.addPaymentMethod(method);
        return new ResponseEntity<Boolean>( true, HttpStatus.OK);
    }


}

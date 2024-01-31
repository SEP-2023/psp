package com.project.auth_service.controller;

import com.project.auth_service.dto.AddPaymentMethodDto;
import com.project.auth_service.dto.PaymentMethodDto;
import com.project.auth_service.model.PaymentMethod;
import com.project.auth_service.service.LoggerService;
import com.project.auth_service.service.PaymentMethodService;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;
    Dotenv dotenv = Dotenv.load();
    String agencyId = dotenv.get("AGENCY_ID");

    private final LoggerService logger = new LoggerService(this.getClass());

    @GetMapping("/payment-methods/{agencyId}")
    public ResponseEntity<List<PaymentMethodDto>> getShopPaymentMethods(@PathVariable String agencyId){
        logger.info(MessageFormat.format("Acquiring payment methods for agency with id {0}", agencyId));

        System.out.println(agencyId);
        Dotenv dotenv = Dotenv.load();
        String agencyIdVar = dotenv.get("AGENCY_ID");
        List<PaymentMethod> dtos = paymentMethodService.getPaymentMethods(agencyIdVar);
        List<PaymentMethodDto> methods = new ArrayList<>();
        for (PaymentMethod m: dtos){
            PaymentMethodDto p = new PaymentMethodDto();
            p.setPaymentMethod(m.getPaymentMethod());
            methods.add(p);
        }
        System.out.println(methods);
        logger.success(MessageFormat.format("Successfully acquired payment methods for agency with id {0}", agencyId));
        return new ResponseEntity<>(methods, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('UPDATE_PAYMENT_METHODS_PERMISSION')")
    @PostMapping("/payment-methods")
    public ResponseEntity<Boolean> addPaymentMethod(@RequestBody AddPaymentMethodDto paymentInfo){
        logger.info(MessageFormat.format("Adding payment method for agency with id {0}", paymentInfo.getAgencyId()));
        PaymentMethod method = new PaymentMethod();
        method.setPaymentMethod(paymentInfo.getPaymentMethod());
        method.setAgencyId(paymentInfo.getAgencyId());
        paymentMethodService.addPaymentMethod(method);
        logger.success(MessageFormat.format("Successfully added new payment method for agency with id {0}", paymentInfo.getAgencyId()));
        return new ResponseEntity<Boolean>( true, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('UPDATE_PAYMENT_METHODS_PERMISSION')")
    @GetMapping("/remove-payment-methods/{method}")
    public ResponseEntity<Boolean> removePaymentMethod(@PathVariable String method){
        logger.info(MessageFormat.format("Removing payment method for agency with id {0}", agencyId));
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setPaymentMethod(method);
        paymentMethod.setAgencyId(agencyId);
        System.out.println(method);
        paymentMethodService.removePaymentMethod(paymentMethod);
        logger.info(MessageFormat.format("Successfully removed payment method for agency with id {0}", agencyId));
        return new ResponseEntity<Boolean>( true, HttpStatus.OK);
    }


}

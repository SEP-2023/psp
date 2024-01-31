package com.project.bank.payment.controller;

import com.project.bank.payment.dto.CompleteTransactionDto;
import com.project.bank.payment.dto.PaymentUrlRequestDto;
import com.project.bank.payment.service.LoggerService;
import com.project.bank.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    private LoggerService logger = new LoggerService(this.getClass());

    @PostMapping("/getPaymentUrl")
    public ResponseEntity<?> getPaymentUrl(@RequestBody PaymentUrlRequestDto dto){
        logger.info(MessageFormat.format("Acquiring payment url for merchant with ID {0}", dto.getMerchantId()));
        return new ResponseEntity<>(paymentService.getPaymentUrl(dto), HttpStatus.OK);
    }

    @PostMapping("/completeTransaction")
    public ResponseEntity<?> completeTransaction(@RequestBody CompleteTransactionDto dto){
        logger.info(MessageFormat.format("Completing transaction with ID {0}", dto.getPaymentId()));
        return new ResponseEntity<>(paymentService.completeTransaction(dto), HttpStatus.OK);
    }
}

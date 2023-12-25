package com.project.crypto.controller;

import com.project.crypto.dto.CreatePaymentDto;
import com.project.crypto.model.PaymentResponse;
import com.project.crypto.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CryptoController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-payment")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody CreatePaymentDto paymentDto) {
        PaymentResponse payment = paymentService.createOrder(paymentDto);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @GetMapping("/confirm/{id}")
    public ResponseEntity<String> confirmPayment(@PathVariable("id") String transactionId){
        paymentService.confirmOrder(transactionId);
        return new ResponseEntity<>("Transaction confirmed.", HttpStatus.OK);
    }

    @GetMapping("/cancel/{id}")
    public ResponseEntity<String> cancelPayment(@PathVariable("id") String transactionId){
        paymentService.cancelOrder(transactionId);
        return new ResponseEntity<>("Transaction canceled.", HttpStatus.OK);
    }

}

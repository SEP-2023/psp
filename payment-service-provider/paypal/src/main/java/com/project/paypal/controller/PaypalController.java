package com.project.paypal.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.project.paypal.dto.ConfirmPaymentDto;
import com.project.paypal.dto.CreatePaymentDto;
import com.project.paypal.dto.ExecutionPaymentResultDto;
import com.project.paypal.dto.RedirectToPaypalDto;
import com.project.paypal.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaypalController {

    private final PaymentService paymentService;

    @PostMapping("/create-payment")
    public ResponseEntity<RedirectToPaypalDto> createPayment(@RequestBody CreatePaymentDto amount) {
        try {
            Payment payment = paymentService.createPayment(amount);

            System.out.println(payment);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    RedirectToPaypalDto response = new RedirectToPaypalDto();
                    response.setRedirectUrl(link.getHref());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new RedirectToPaypalDto(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> executePayment(@RequestBody ConfirmPaymentDto dto) {

        try {
            boolean executed = paymentService.executePayment(dto);
            if (executed) {
                ExecutionPaymentResultDto res = new ExecutionPaymentResultDto();
                res.setExecuted(true);
                paymentService.saveSuccessfulTransaction(dto);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                paymentService.saveFailedTransaction(dto);
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<ExecutionPaymentResultDto>(HttpStatus.BAD_REQUEST);
    }
}

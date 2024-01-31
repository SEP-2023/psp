package com.project.paypal.controller;

import com.paypal.api.payments.Agreement;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.project.paypal.dto.*;
import com.project.paypal.service.LoggerService;
import com.project.paypal.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.MessageFormat;


@RestController
@RequiredArgsConstructor
public class PaypalController {

    private final PaymentService paymentService;

    private final LoggerService logger = new LoggerService(this.getClass());

    @PostMapping("/create-payment")
    public ResponseEntity<RedirectToPaypalDto> createPayment(@RequestBody CreatePaymentDto amount) {
        try {
            logger.info("Initiating transaction...");
            Payment payment = paymentService.createPayment(amount);

            System.out.println(payment);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    RedirectToPaypalDto response = new RedirectToPaypalDto();
                    response.setRedirectUrl(link.getHref());
                    logger.success(MessageFormat.format("Successfully created transaction with ID {0}", payment.getId()));
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
        } catch (PayPalRESTException e) {
            logger.error("Transaction failed");
            e.printStackTrace();
        }
        return new ResponseEntity<>(new RedirectToPaypalDto(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> executePayment(@RequestBody ConfirmPaymentDto dto) {
        logger.info("Processing transaction...");
        try {
            boolean executed = paymentService.executePayment(dto);
            if (executed) {
                logger.success(MessageFormat.format("Payment with id {0} successfully executed", dto.getPaymentId()));
                ExecutionPaymentResultDto res = new ExecutionPaymentResultDto();
                res.setExecuted(true);
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
            logger.error(MessageFormat.format("Payment with id {0} failed to execute", dto.getPaymentId()));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        logger.warn(MessageFormat.format("Payment with id {0} failed to execute", dto.getPaymentId()));
        return new ResponseEntity<ExecutionPaymentResultDto>(HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/create-subscription")
    public ResponseEntity<?> createSubscription(@RequestBody CreateSubscriptionDto info) {
        System.out.println("tu je");
        logger.info(MessageFormat.format("Creating subscription for agency with ID {0}", info.getAgencyId()));

        try {
            Agreement agreement = paymentService.createSubscription(info);
            for (Links link : agreement.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    RedirectToPaypalDto dto = new RedirectToPaypalDto();
                    dto.setRedirectUrl(link.getHref());
                    dto.setToken(agreement.getToken());
                    return new ResponseEntity<>(dto, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PayPalRESTException e) {
            logger.error(MessageFormat.format("Failed to create subscription for agency with ID {0}", info.getAgencyId()));
            System.out.println(e.getMessage());
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            logger.error(MessageFormat.format("Failed to create subscription for agency with ID {0}", info.getAgencyId()));
            e.printStackTrace();
        }
        logger.error(MessageFormat.format("Failed to create subscription for agency with ID {0}", info.getAgencyId()));
        return new ResponseEntity<ExecutionPaymentResultDto>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/execute-subscription/{token}")
    public ResponseEntity<Boolean> executeSubscription(@PathVariable String token) {
        logger.info("Executing subscription...");
        try {
            if (paymentService.executeSubscription(token)) {
                logger.success("Subscription successfully executed");
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                logger.error("Failed to execute subscription");
                return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (PayPalRESTException e) {
            logger.error("Failed to execute subscription");
            e.printStackTrace();
        }
        logger.error("Failed to execute subscription");
        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

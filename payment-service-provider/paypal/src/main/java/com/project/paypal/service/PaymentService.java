package com.project.paypal.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.project.paypal.dto.ConfirmPaymentDto;
import com.project.paypal.dto.CreatePaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class PaymentService {

     private final APIContext apiContext;

    public Payment createPayment(CreatePaymentDto dto) throws PayPalRESTException {

        Payment payment = new Payment();

        List<Transaction> transactions = new ArrayList<>();

        Amount amount = new Amount();
        amount.setTotal(dto.getAmount().toString());
        amount.setCurrency("USD");
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("Testing paypal");

        transactions.add(transaction);
        payment.setTransactions(transactions);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        payment.setPayer(payer);

        payment.setIntent("SALE");

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl( "http://localhost:4200/api/cancel-paypal-payment" );
        String returnLink = "http://localhost:4200/confirm-payment?price="+dto.getAmount()+ "&transactionId=" + dto.getTransactionId()+ "&agencyId="+dto.getAgencyId();
        redirectUrls.setReturnUrl(returnLink);
        payment.setRedirectUrls(redirectUrls);
        apiContext.setMaskRequestId(true);
        return payment.create(apiContext);
    }

    public boolean executePayment(ConfirmPaymentDto dto) throws PayPalRESTException {
        boolean executed=false;
        Payment payment = new Payment();
        payment.setId(dto.getPaymentId());
        PaymentExecution paymentExecute = new PaymentExecution();
        System.out.println(dto.getPayerId());
        paymentExecute.setPayerId(dto.getPayerId());
        payment=payment.execute(apiContext, paymentExecute);
        if (payment.getState().equals("approved")) {
           executed=true;
        }
        return executed;
    }
}

package com.project.crypto.service;
import com.project.crypto.dto.CreatePaymentDto;
import com.project.crypto.model.CryptoTransaction;
import com.project.crypto.model.PaymentResponse;
import com.project.crypto.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
public class PaymentService {

    String urlCoinGate = "https://api-sandbox.coingate.com/api/v2";

    @Autowired
    private TransactionRepository transactionRepository;

    public PaymentResponse createOrder(CreatePaymentDto createPaymentDto) {

        //Merchant merchant = merchantRepository.findByMerchantId(createOrderDto.getShopId());

        String body = "order_id=" + createPaymentDto.getTransactionId() + "&" +
                "price_amount=" + createPaymentDto.getAmount() + "&" + //createOrderDto.getAmount()
                "price_currency=EUR" + "&" +
                "receive_currency=EUR" + "&" +
                "title=" + createPaymentDto.getAgencyId() + "_" + createPaymentDto.getTransactionId() + "&" +

                //body.append("callback_url=").append(URLEncoder.encode(urlPSPCrypto+"callback", StandardCharsets.UTF_8)).append("&");
                "cancel_url=" + URLEncoder.encode( "http://localhost:4200/cancel-crypto?transactionId=", StandardCharsets.UTF_8) + createPaymentDto.getTransactionId() + "&" +
                "success_url=" + URLEncoder.encode( "http://localhost:4200/successful-crypto?transactionId=", StandardCharsets.UTF_8) + createPaymentDto.getTransactionId();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlCoinGate +"/orders"))
                .header("accept", "application/json")
                .header("content-type", "application/x-www-form-urlencoded")
                .header("Authorization", "Token " + "zVi-zRdiNce4fDrcmsgN9MHMM-vWG5SpHqNZChNx")
                .method("POST", HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        PaymentResponse paymentResponse = gson.fromJson(response.body(), PaymentResponse.class);

        CryptoTransaction cryptoTransaction = new CryptoTransaction();
        cryptoTransaction.setTransactionId(createPaymentDto.getTransactionId());
        cryptoTransaction.setAmount(createPaymentDto.getAmount());
        cryptoTransaction.setPriceCurrency("EUR");
        cryptoTransaction.setTitle(createPaymentDto.getAgencyId());
        cryptoTransaction.setStatus("NEW");
        cryptoTransaction.setAgencyId(createPaymentDto.getAgencyId());
        transactionRepository.saveAndFlush(cryptoTransaction);

        return paymentResponse;

    }


    public void confirmOrder(String orderId) {
        CryptoTransaction cryptoTransaction = transactionRepository.findCryptoTransactionById(Long.parseLong(orderId));
        cryptoTransaction.setStatus("PAID");
        transactionRepository.save(cryptoTransaction);
    }

    public void cancelOrder(String orderId) {
        CryptoTransaction cryptoTransaction = transactionRepository.findCryptoTransactionById(Long.parseLong(orderId));
        cryptoTransaction.setStatus("CANCELED");
        transactionRepository.save(cryptoTransaction);
    }




}
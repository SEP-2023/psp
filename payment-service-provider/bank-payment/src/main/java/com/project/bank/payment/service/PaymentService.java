package com.project.bank.payment.service;

import com.project.bank.payment.dto.*;
import com.project.bank.payment.model.Bank;
import com.project.bank.payment.model.Merchant;
import com.project.bank.payment.model.Transaction;
import com.project.bank.payment.model.TransactionStatus;
import com.project.bank.payment.repository.BankRepository;
import com.project.bank.payment.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Service
public class PaymentService {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private TransactionService transactionService;

    public PaymentUrlResponseDto getPaymentUrl(PaymentUrlRequestDto dto) {
        Merchant merchant = merchantService.findByMerchantId(dto.getMerchantId());
        InitialRequestDto request = new InitialRequestDto();
        request.setAmount(dto.getAmount());
        request.setMerchantId(dto.getMerchantId());
        request.setMerchantOrderId(dto.getMerchantOrderId());
        request.setMerchantPassword(merchant.getMerchantPassword());
        request.setSuccessUrl(dto.getSuccessUrl());
        request.setErrorUrl(dto.getErrorUrl());
        request.setFailedUrl(dto.getFailedUrl());
        PaymentUrlResponseDto response = getPaymentUrlFromBank(merchant.getBank().getUrl(), request);

        Transaction t = new Transaction();
        t.setAmount(request.getAmount());
        t.setMerchantOrderId(request.getMerchantOrderId());
        t.setMerchantTimestamp(request.getMerchantTimestamp());
        t.setSuccessUrl(request.getSuccessUrl());
        t.setErrorUrl(request.getErrorUrl());
        t.setFailedUrl(request.getFailedUrl());
        t.setStatus(TransactionStatus.INITIATED);
        t.setMerchant(merchant);
        if(response != null){
            t.setPaymentId(response.getPaymentId());
        } else {
            t.setStatus(TransactionStatus.ERROR);
        }

        transactionService.save(t);

        return response;
    }

    private PaymentUrlResponseDto getPaymentUrlFromBank(String bankUrl, InitialRequestDto request) {
        String url = bankUrl + "getPaymentUrl";
            ResponseEntity<PaymentUrlResponseDto> response = WebClient.builder()
                    .build().post()
                    .uri(url)
                    .body(BodyInserters.fromValue(request))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(PaymentUrlResponseDto.class)
                    .block();

            if (response != null && response.getStatusCode() == HttpStatus.OK) {
                System.out.println(response.getBody());
                return response.getBody();
            } else {
                return null;
            }

    }

    public StatusResponse completeTransaction(CompleteTransactionDto dto) {
        Transaction t = transactionService.findByPaymentId(dto.getPaymentId());
        t.setStatus(TransactionStatus.fromString(dto.getTransactionStatus()));
        t.setMerchantOrderId(dto.getMerchantOrderId());
        t.setAcquirerOrderId(dto.getAcquirerOrderId());
        t.setAcquirerTimestamp(dto.getAcquirerTimestamp());
        transactionService.save(t);
        StatusResponse status = new StatusResponse();
        if(t.getStatus().equals(TransactionStatus.ERROR)){
            status.setStatus(t.getErrorUrl());
        } else if (t.getStatus().equals(TransactionStatus.FAILED)){
            status.setStatus(t.getFailedUrl());
        } else {
            status.setStatus(t.getSuccessUrl());
        }
        return status;
    }
}

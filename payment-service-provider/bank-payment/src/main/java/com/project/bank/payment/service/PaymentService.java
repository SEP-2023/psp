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

import java.text.MessageFormat;
import java.util.ArrayList;

@Service
public class PaymentService {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private TransactionService transactionService;

    private LoggerService logger = new LoggerService(this.getClass());


    public PaymentUrlResponseDto getPaymentUrl(PaymentUrlRequestDto dto) {
        logger.info(MessageFormat.format("Start creating payment url for merchant with ID {0}", dto.getMerchantId()));
        Merchant merchant = merchantService.findByMerchantId(dto.getMerchantId());
        InitialRequestDto request = new InitialRequestDto();
        request.setAmount(dto.getAmount());
        request.setMerchantId(dto.getMerchantId());
        request.setMerchantOrderId(dto.getMerchantOrderId());
        request.setMerchantPassword(merchant.getMerchantPassword());
        request.setSuccessUrl(dto.getSuccessUrl());
        request.setErrorUrl(dto.getErrorUrl());
        request.setFailedUrl(dto.getFailedUrl());
        request.setQr(dto.isQr());
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
            logger.success("Successfully acquired paymentUrl from the bank");
            t.setPaymentId(response.getPaymentId());
        } else {
            logger.error("Error while acquiring paymentUrl from the bank");
            t.setStatus(TransactionStatus.ERROR);
        }

        transactionService.save(t);
        logger.success("Initial transaction info saved.");
        return response;
    }

    private PaymentUrlResponseDto getPaymentUrlFromBank(String bankUrl, InitialRequestDto request) {
        logger.info("Sending request to the acquirer bank to get paymentUrl");
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
        logger.info(MessageFormat.format("Finishing transaction with ID {0} ...", dto.getPaymentId()));
        Transaction t = transactionService.findByPaymentId(dto.getPaymentId());
        t.setStatus(TransactionStatus.fromString(dto.getTransactionStatus()));
        t.setMerchantOrderId(dto.getMerchantOrderId());
        t.setAcquirerOrderId(dto.getAcquirerOrderId());
        t.setAcquirerTimestamp(dto.getAcquirerTimestamp());
        transactionService.save(t);
        StatusResponse status = new StatusResponse();
        if(t.getStatus().equals(TransactionStatus.ERROR)){
            logger.error(MessageFormat.format("Error while finishing transaction with ID {0}", dto.getPaymentId()));
            status.setStatus(t.getErrorUrl());
        } else if (t.getStatus().equals(TransactionStatus.FAILED)){
            logger.info(MessageFormat.format("Failed to finish the transaction with ID {0}", dto.getPaymentId()));
            status.setStatus(t.getFailedUrl());
        } else {
            logger.success(MessageFormat.format("Successfully finished the transaction with ID {0}", dto.getPaymentId()));
            status.setStatus(t.getSuccessUrl());
        }
        return status;
    }
}

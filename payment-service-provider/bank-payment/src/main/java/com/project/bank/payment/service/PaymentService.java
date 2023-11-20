package com.project.bank.payment.service;

import com.project.bank.payment.dto.InitialRequestDto;
import com.project.bank.payment.dto.PaymentUrlRequestDto;
import com.project.bank.payment.dto.PaymentUrlResponseDto;
import com.project.bank.payment.model.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PaymentService {

    @Autowired
    private MerchantService merchantService;

    public PaymentUrlResponseDto getPaymentUrl(PaymentUrlRequestDto dto) {
        Merchant merchant = merchantService.findByMerchantId(dto.getMerchantId());
        InitialRequestDto request = new InitialRequestDto();
        request.setAmount(dto.getAmount());
        request.setMerchantId(dto.getMerchantId());
        request.setMerchantOrderId(dto.getMerchantOrderId());
        PaymentUrlResponseDto response = getPaymentUrlFromBank(merchant.getBank().getUrl(), request);
        if(response == null){
            // greska
        }
        return response;
    }

    private PaymentUrlResponseDto getPaymentUrlFromBank(String bankUrl, InitialRequestDto request) {
        String url = bankUrl + "getPaymentUrl";
        try {
            ResponseEntity<PaymentUrlResponseDto> response = WebClient.builder()
                    .build().post()
                    .uri(url)
                    .body(BodyInserters.fromValue(request))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(PaymentUrlResponseDto.class)
                    .block();

            if (response != null && response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }
}

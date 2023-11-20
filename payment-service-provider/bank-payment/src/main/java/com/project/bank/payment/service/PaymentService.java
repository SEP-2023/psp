package com.project.bank.payment.service;

import com.project.bank.payment.dto.InitialRequestDto;
import com.project.bank.payment.dto.PaymentUrlRequestDto;
import com.project.bank.payment.dto.PaymentUrlResponseDto;
import com.project.bank.payment.model.Bank;
import com.project.bank.payment.model.Merchant;
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
    private BankRepository bankRepository;
    @Autowired
    private MerchantRepository merchantRepository;

    public PaymentUrlResponseDto getPaymentUrl(PaymentUrlRequestDto dto) {
        initData();
        Merchant merchant = merchantService.findByMerchantId(dto.getMerchantId());
        InitialRequestDto request = new InitialRequestDto();
        request.setAmount(dto.getAmount());
        request.setMerchantId(dto.getMerchantId());
        request.setMerchantOrderId(dto.getMerchantOrderId());
        request.setMerchantPassword(merchant.getMerchantPassword());
        PaymentUrlResponseDto response = getPaymentUrlFromBank(merchant.getBank().getUrl(), request);
        if(response == null){
            // greska
        }
        return response;
    }

    private void initData() {
        Bank b = new Bank();
        b.setUrl("http://localhost:8087/");
        b.setName("unicredit");
        b.setBin("1111");
        b.setMerchants(new ArrayList<>());
        Bank bank = bankRepository.save(b);

        Merchant m = new Merchant();
        m.setBank(bank);
        m.setTransactions(new ArrayList<>());
        m.setMerchantId("nekiId");
        m.setMerchantPassword("pass");
        merchantRepository.save(m);
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
                System.out.println(response);

                return null;
            }

    }
}

package com.project.paypal.service;

import com.paypal.api.payments.*;
import com.paypal.api.payments.Currency;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.project.paypal.dto.ConfirmPaymentDto;
import com.project.paypal.dto.CreatePaymentDto;
import com.project.paypal.dto.CreateSubscriptionDto;
import com.project.paypal.model.*;
import com.project.paypal.repository.SubscriptionPlanRepository;
import com.project.paypal.repository.SubscriptionRepository;
import com.project.paypal.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.*;


@RequiredArgsConstructor
@Service
public class PaymentService {

     private final APIContext apiContext;
     private final TransactionRepository transactionRepository;
     private final SubscriptionPlanRepository subscriptionPlanRepository;
     private final SubscriptionRepository subscriptionRepository;

//     private final String appUrl = "http://localhost:4200/";
    private final String appUrl = "http://192.168.0.15:4200/";

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
        redirectUrls.setCancelUrl(appUrl + "api/cancel-paypal-payment");
        String returnLink = appUrl + "confirm-payment?price="+dto.getAmount()+ "&transactionId=" + dto.getTransactionId()+ "&agencyId="+dto.getAgencyId();
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

    public void saveSuccessfulTransaction(ConfirmPaymentDto dto){
        PaypalTransaction t = new PaypalTransaction();
        t.setTransactionId(dto.getTransactionId());
        t.setAmount(dto.getAmount());
        t.setAgencyId(dto.getAgencyId());
        t.setTransactionStatus(TransactionStatus.APPROVED);
        transactionRepository.save(t);
    }

    public void saveFailedTransaction(ConfirmPaymentDto dto){
        PaypalTransaction t = new PaypalTransaction();
        t.setTransactionId(dto.getTransactionId());
        t.setAmount(dto.getAmount());
        t.setAgencyId(dto.getAgencyId());
        t.setTransactionStatus(TransactionStatus.FAILED);
        transactionRepository.save(t);
    }

    public Agreement createSubscription(CreateSubscriptionDto dto) throws PayPalRESTException, MalformedURLException, UnsupportedEncodingException {

        SubscriptionPlan subscriptionPlan = new SubscriptionPlan();
        subscriptionPlan.setCurrency("USD");
        subscriptionPlan.setDescription("Subscription plan");
        subscriptionPlan.setAmountValue(dto.getAmount());
        subscriptionPlan.setName("Subscription plan");
        subscriptionPlanRepository.save(subscriptionPlan);
        Plan plan = createPlan(subscriptionPlan, dto);

        Plan requestPlan = new Plan();
        requestPlan.setId(plan.getId());
        apiContext.setMaskRequestId(true);

        Agreement agreement = new Agreement();
        agreement.setName("Payment agreement");
        Date startDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("CET"));
        String formattedStartDate = sdf.format(startDate);
        agreement.setStartDate(formattedStartDate);
        agreement.setPlan(requestPlan);
        agreement.setPayer(new Payer().setPaymentMethod("paypal"));
        agreement.setDescription(subscriptionPlan.getDescription());
        agreement = agreement.create(apiContext);

        Subscription subscription = new Subscription();
        subscription.setName(subscriptionPlan.getDescription());
        subscription.setStartDate(startDate);
        subscription.setToken(agreement.getToken());
        subscription.setStatus(SubscriptionStatus.CREATED);
        subscription.setTransactionId(dto.getTransactionId());
        subscriptionRepository.save(subscription);

        return agreement;

    }

    public boolean executeSubscription(String token) throws PayPalRESTException {
        Subscription subscription = subscriptionRepository.getSubscriptionByToken(token);
        boolean operationResult = false;
        if (subscription != null) {
            Agreement agreementAfterExecution = Agreement.execute(apiContext, token);
            subscription.setSubscriberId(agreementAfterExecution.getPayer().getPayerInfo().getPayerId());
            subscription.setSubscriberMail(agreementAfterExecution.getPayer().getPayerInfo().getEmail());
            subscription.setStatus(SubscriptionStatus.APPROVED);
            subscriptionRepository.save(subscription);
            operationResult=true;
        }
        return operationResult;

    }

    private Plan createPlan(SubscriptionPlan subscriptionPlan, CreateSubscriptionDto dto) throws PayPalRESTException {
        Plan plan = new Plan();

        plan.setName(subscriptionPlan.getName());
        plan.setDescription(subscriptionPlan.getDescription());
        plan.setType("fixed");

        PaymentDefinition paymentDefinition = new PaymentDefinition();
        paymentDefinition.setName("Monthly Payments");
        paymentDefinition.setFrequency("YEAR");
        paymentDefinition.setType("REGULAR");
        paymentDefinition.setFrequencyInterval("1");
        paymentDefinition.setCycles("12");

        Currency currency = new Currency();
        currency.setValue(subscriptionPlan.getAmountValue());
        currency.setCurrency(subscriptionPlan.getCurrency());
        paymentDefinition.setAmount(currency);

        ChargeModels chargeModels = new ChargeModels();
        chargeModels.setType("TAX");
        chargeModels.setAmount(new Currency().setCurrency("USD").setValue("0"));
        List<ChargeModels> chargeModelsList = new ArrayList<ChargeModels>();
        chargeModelsList.add(chargeModels);
        paymentDefinition.setChargeModels(chargeModelsList);

        List<PaymentDefinition> paymentDefinitionList = new ArrayList<PaymentDefinition>();
        paymentDefinitionList.add(paymentDefinition);
        plan.setPaymentDefinitions(paymentDefinitionList);

        String confirmUrl = appUrl + "confirm-subscription?transactionId="+ dto.getTransactionId()+"&agencyId="+dto.getAgencyId()+"&amount="+dto.getAmount();
        String cancelUrl = appUrl + "cancel";

        MerchantPreferences merchantPreferences = new MerchantPreferences();
        merchantPreferences.setSetupFee(currency);
        merchantPreferences.setCancelUrl(cancelUrl);
        merchantPreferences.setReturnUrl(confirmUrl);
        merchantPreferences.setMaxFailAttempts("0");
        merchantPreferences.setInitialFailAmountAction("CONTINUE");
        merchantPreferences.setAutoBillAmount("YES");
        plan.setMerchantPreferences(merchantPreferences);
        plan = plan.create(apiContext);

        List<Patch> patchRequestList = new ArrayList<Patch>();
        Map<String, String> value = new HashMap<String, String>();
        value.put("state", "ACTIVE");
        Patch patch = new Patch();
        patch.setPath("/");
        patch.setValue(value);
        patch.setOp("replace");
        patchRequestList.add(patch);

        plan.update(apiContext, patchRequestList);
        return plan;
    }
}

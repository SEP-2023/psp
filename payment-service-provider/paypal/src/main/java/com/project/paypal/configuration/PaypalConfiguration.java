package com.project.paypal.configuration;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaypalConfiguration {


    String clientId = "AYZbK31Co6zH6D0U6gU3OyEkE9HQ54-jSfBG4ibRgQD6tzt6SxSIkyWlEIoEtUXQYlJo0nldpn70zrzS";
           //  "AQo7jtaQwfbGrAwz1wEqMPiL6hhG-tpuG5rNlhZfEtwPMKf8YOlyofSJlXhY7z1UmjgaZGfbW237iz_5";
    String clientSecret = "EBXGH50yZyudlMq7uMbTQIVX_uEmFoCjRtcY_X_ECZok8kaA8loZnVq18AVExx69_ieSm8aWMUStBSxn";
                   // "EHx6KiMDHO-U_G_RnzA0CSQfOogb590uYCe609htoRipTKUN2VXrVZczX9L4NKSD5GJ9m9H1-M6ux4AK";

    @Bean
    public OAuthTokenCredential oAuthTokenCredential(){
        return new OAuthTokenCredential(clientId,clientSecret,paypalSDKConfig());
    }

    @Bean
    public Map<String, String> paypalSDKConfig() {
        Map<String,String> config=new HashMap<>();
        config.put("mode","sandbox");
        return config;
    }

    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        APIContext context=new APIContext(oAuthTokenCredential().getAccessToken());
        context.setConfigurationMap(paypalSDKConfig());
        return context;
    }
}

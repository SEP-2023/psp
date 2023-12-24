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


    String clientId = "AQo7jtaQwfbGrAwz1wEqMPiL6hhG-tpuG5rNlhZfEtwPMKf8YOlyofSJlXhY7z1UmjgaZGfbW237iz_5";
    String clientSecret = "EHx6KiMDHO-U_G_RnzA0CSQfOogb590uYCe609htoRipTKUN2VXrVZczX9L4NKSD5GJ9m9H1-M6ux4AK";

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

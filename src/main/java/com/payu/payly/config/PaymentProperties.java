package com.payu.payly.config;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class PaymentProperties {
      @Value("${invoice.redirect.url}")
    private String invoiceRedirectUrl;
        @Value("${paystack.url}")
    private String paystackUrl;
       @Value("${paystack.secret.key}")
    private String paystackSecretKey;
}

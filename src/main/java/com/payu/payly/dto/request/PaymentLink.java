package com.payu.payly.dto.request;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentLink {
    private String paymentUrl;
    private String transactionReference;
}

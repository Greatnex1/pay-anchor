package com.payu.payly.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceUrlRequest {
    @Positive (message = "invoice id can not be negative")
    private long invoiceId;
    @Positive (message = "merchant id can not be negative")
    private long merchantId;
}

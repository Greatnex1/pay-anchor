package com.payu.payly.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class InvoiceDto {
    private long invoiceId;
    private long merchantId;
    private String url;
}

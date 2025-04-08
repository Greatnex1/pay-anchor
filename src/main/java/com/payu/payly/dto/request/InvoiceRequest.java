package com.payu.payly.dto.request;

import com.payu.payly.model.ProductDetail;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceRequest {
    @NotBlank(message = "Merchant name is required")
    private String merchantName;
    @NotEmpty(message = "Merchant address is required")
    private String merchantAddress;
    @Email(message= "Invalid email address")
    private String merchantEmail;
    @NotEmpty(message = "Product details required")
    private List<ProductDetail> productDetails;
}

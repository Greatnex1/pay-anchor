package com.payu.payly.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {
    @NotEmpty
    private String productName;

    private BigDecimal productPrice;
    @Positive
    private int productQuantity;
}

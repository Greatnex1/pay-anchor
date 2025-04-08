package com.payu.payly.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {
    @NotEmpty
    @Column(length = 500)
    private String productName;
    @Positive
    private BigDecimal productPrice;
    @Positive
    private int productQuantity;

}

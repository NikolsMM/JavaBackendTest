package com.nikols.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Integer productId;
    private String productName;
    private String color;
    private Double price;
    private Integer quantity;
    private Integer customerId;

}

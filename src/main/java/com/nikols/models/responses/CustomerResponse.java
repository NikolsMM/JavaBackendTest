package com.nikols.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    private Integer customerId;
    private String firstName;
    private String email;
    private Integer age;
    private List<ProductResponse> products;
    //Para no crear bucles de JSON infinitos, podemos ProductResponse en lugar de Product.
    //En Products, ponemos un Customer customer y no response/request porque ya estará creado.

}

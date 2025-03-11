package com.nikols.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentRequest {
    @NotBlank(message = "You must specify the card type.")
    private String cardType;
    @NotBlank(message = "You must specify the expiration date.")
    private String expirationDate;
    @NotNull(message = "You must specify the customer associated to this payment method.")
    private Integer customerId;

}

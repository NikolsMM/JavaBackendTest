package com.nikols.models.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String productName;

    @NotBlank(message = "El color es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String color;

    @NotNull(message = "Debes especificar el precio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
    private Double price;

    @NotNull(message = "Debes especificar la cantidad")
    @Min(value = 1, message = "La cantidad debe ser mayor que 0")
    private Integer quantity;

}

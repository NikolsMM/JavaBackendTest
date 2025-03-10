package com.nikols.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "product_name")
    private String productName;
    private String color;
    private Double price;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY) //Porque MANY products llegan a ONE customer, el Lazy por si hay muchos tipos. Sino por defecto es EAGER
    @JoinColumn(name = "customer_id", nullable = true) //Clave foranea
    private Customer customer;
}

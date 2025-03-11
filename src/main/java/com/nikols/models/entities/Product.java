package com.nikols.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinColumn(name = "customer_id", nullable = true) //Clave foranea, nullabe=true significa que un producto lo podemos dejar sin customerId. Si estuviera en false, forzamos a que to do producto tenga que tener un customerId asociado-
    @JsonIgnore //Para evitar bucles infinitos de Json  como -> @JsonManagedReference + @JsonBackReference //Evita recursión infinita de customer -> products -> for each product un customer -> products ...
    private Customer customer;
}

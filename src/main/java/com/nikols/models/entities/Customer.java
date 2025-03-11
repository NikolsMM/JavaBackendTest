package com.nikols.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity //sino es solo un objeto, con @Entity es una entidad
//Esto es de Lombok para ahorrar codigo innecesario de [1]
@Table(name = "customers") //Para crearlo en la base de datos
@Data //-> getters, setters, toStrings, equals, hashCode
@NoArgsConstructor //-> constructor vacio
@AllArgsConstructor //-> constructor entero
@JsonIgnoreProperties(ignoreUnknown = true) //Ignorar campos desconocidos.
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "first_name")
    private String firstName;
    private String email;
    private Integer age;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //Porque ONE customer llega a MANY products
    //mappedBy para que no se cree una clave foranea extra porque ya la estamos creando en Products
    //cascade -> si se borra un customer,se borran sus products.
    private List<Product> products;

    /* To do esto es [1]
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(email, customer.email) && Objects.equals(age, customer.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, age);
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Customer() {
    }

    public Customer(Integer id, String name, String email, String age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }
    */
}

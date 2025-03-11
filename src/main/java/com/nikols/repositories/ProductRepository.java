package com.nikols.repositories;

import com.nikols.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByProductName(String productName);
    List<Product> findByColor(String color);
    List<Product> findByCustomer_CustomerId(Integer customerId);
}

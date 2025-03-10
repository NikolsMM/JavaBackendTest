package com.nikols.service;

import com.nikols.models.requests.ProductRequest;
import com.nikols.models.responses.ProductResponse;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {
    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Integer id);
    ProductResponse saveProduct(ProductRequest request);
    void deleteProductById(Integer id);

    ProductResponse modifyProductPrice(Integer id, Double newPrice);
    ProductResponse modifyProductQuantity(Integer id, Integer newQuantity);
    Optional<ProductResponse> getProductByName(String name);
    List<ProductResponse> getProductsByColor(String color);
    ProductResponse modifyProduct(Integer id, Map<String, Object> updates);
    List<ProductResponse> findByCustomerCustomerId (Integer customerId);
}

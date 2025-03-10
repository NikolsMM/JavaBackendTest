package com.nikols.controllers;

import com.nikols.models.requests.ProductRequest;
import com.nikols.models.responses.ProductResponse;
import com.nikols.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse findProductById(@PathVariable("id") Integer id){
        return productService.getProductById(id);
    }

    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request){
        return productService.saveProduct(request);
    }

    @GetMapping("/name/{name}")
    public Optional<ProductResponse> findProductsByName(@PathVariable("name") String name){
        return productService.getProductByName(name);
    }

    @GetMapping("/color/{color}")
    public List<ProductResponse> findProductByEmail(@PathVariable("color") String color){
        return productService.getProductsByColor(color);
    }

    @PatchMapping("/{id}/{price}")
    public ProductResponse modifyProductPrice(@PathVariable("id") Integer id, @PathVariable("price") Double price){
        return productService.modifyProductPrice(id, price);
    }

    @PatchMapping("/{id}/{quantity}")
    public ProductResponse modifyProductQuantity(@PathVariable("id") Integer id, @PathVariable("quantity") Integer quantity){
        return productService.modifyProductQuantity(id, quantity);
    }

    @PatchMapping("/{id}")
    public ProductResponse modifyProduct(@PathVariable("id") Integer id,  @RequestBody Map<String, Object> updates){
        return productService.modifyProduct(id, updates);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Integer id){
        productService.deleteProductById(id);
    }

    @GetMapping("/customer-products/{id}")
    public List<ProductResponse> findByCustomerCustomerId (@PathVariable("id") Integer customerId){
        return productService.findByCustomerCustomerId(customerId);
    }
}
package com.nikols.service;

import com.nikols.mapper.ProductMapper;
import com.nikols.models.entities.Customer;
import com.nikols.models.entities.Product;
import com.nikols.models.requests.ProductRequest;
import com.nikols.models.responses.ProductResponse;
import com.nikols.repositories.CustomerRepository;
import com.nikols.repositories.ProductRepository;
import com.nikols.utils.JsonHelper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{


    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final ProductMapper productMapper;    
    
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductResponse getProductById(Integer id) {
        return productRepository.findById(id)
                .map(productMapper::toResponse)
                .orElse(null);
    }

    @Override
    @Transactional
    public ProductResponse saveProduct(ProductRequest request) {
        Product product = productMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional
    public void deleteProductById(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ProductResponse modifyProductPrice(Integer id, Double newPrice){
        if (newPrice == null || newPrice <= 0) {
            throw new RuntimeException("Price must be greater than 0.");
        }
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Product with id: " + id + " not found"));
        product.setPrice(newPrice);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponse modifyProductQuantity(Integer id, Integer newQuantity){
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Product with id: " + id + " not found"));
        product.setQuantity(newQuantity);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }
    @Override
    @Transactional
    public Optional<ProductResponse> getProductByName(String productName) {
        return productRepository.findByProductName(productName)
                .map(productMapper::toResponse);
    }

    @Override
    @Transactional
    public List<ProductResponse> getProductsByColor(String color) {
        return productRepository.findByColor(color)
                .stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductResponse modifyProduct(Integer id, Map<String, Object> updates) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with id " + id + " does not exist."));

        if (updates == null || updates.isEmpty()) {
            return productMapper.toResponse(product);
        }

        JsonHelper.mergeJsonIntoObject(product, updates); // 🔹 Modifica la misma instancia

        productRepository.save(product); // Hibernate detectará los cambios automáticamente

        return productMapper.toResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse assignProductToCustomer(Integer productId, Integer customerId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product with id: " + productId + " not found."));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer with id: " + customerId + " not found."));
        product.setCustomer(customer);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional
    public List <ProductResponse> findByCustomer_CustomerId (Integer customerId) {
        if (customerRepository.findById(customerId).isEmpty() ){
            throw new RuntimeException("Customer with id: " + customerId + " not found");
        }
        return productRepository.findByCustomer_CustomerId(customerId)
                .stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }
    
}

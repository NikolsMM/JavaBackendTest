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

    //@TODO tenego que hacer que el customerId no sea request pero que se le pueda mete ry lo busque etc.
    @Override
    @Transactional
    public ProductResponse saveProduct(ProductRequest request) {
        Product product = productMapper.toEntity(request);

        if (request.getCustomerId() != null) {
            Customer customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            product.setCustomer(customer);
        }

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
                .orElseThrow(() -> new RuntimeException("Product with  " + id + " does not exist."));

        if (updates == null || updates.isEmpty()) {
            return productMapper.toResponse(product);
        }

        Product updatedProduct = JsonHelper.mergeJson(product, updates, Product.class);

        Product savedProduct = productRepository.save(updatedProduct);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional
    public List <ProductResponse> findByCustomerCustomerId (Integer customerId) {
        return productRepository.findByCustomerCustomerId(customerId)
                .stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }
    
}

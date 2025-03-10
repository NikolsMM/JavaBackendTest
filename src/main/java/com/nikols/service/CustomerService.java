package com.nikols.service;

import com.nikols.models.entities.Customer;
import com.nikols.models.requests.CustomerRequest;
import com.nikols.models.responses.CustomerResponse;
import com.nikols.models.responses.ProductResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CustomerService {
    List<CustomerResponse> getAllCustomers();
    CustomerResponse getCustomerById(Integer id);
    CustomerResponse saveCustomer(CustomerRequest request);
    void deleteCustomerById(Integer id);

    CustomerResponse modifyCustomerEmail(Integer id, String newEmail);

    //Querys personalizados
    Optional<CustomerResponse> getCustomerByEmail(String email);
    List<CustomerResponse> getCustomersByName(String name);
    List<CustomerResponse> getCustomersByAge(Integer age);

    CustomerResponse modifyCustomer(Integer id, Map<String, Object> updates);
}

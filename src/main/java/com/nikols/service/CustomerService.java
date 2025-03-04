package com.nikols.service;

import com.nikols.models.entities.Customer;
import com.nikols.models.requests.CustomerRequest;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(Integer id);
    Customer saveCustomer(CustomerRequest request);
    void deleteCustomerById(Integer id);

    //Querys personalizados
    Optional<Customer> getCustomerByEmail(String email);
    List<Customer> getCustomersByName(String name);

    List<Customer> getCustomersByAge(Integer age);

}

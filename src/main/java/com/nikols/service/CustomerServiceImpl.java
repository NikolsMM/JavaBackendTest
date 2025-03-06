package com.nikols.service;


import com.nikols.mapper.CustomerMapper;
import com.nikols.models.entities.Customer;
import com.nikols.models.requests.CustomerRequest;
import com.nikols.models.responses.CustomerResponse;
import com.nikols.repositories.CustomerRepository;
import com.nikols.utils.JsonHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse getCustomerById(Integer id) {
        return customerRepository.findById(id)
                .map(customerMapper::toResponse)
                .orElse(null);
    }

    @Override
    public CustomerResponse saveCustomer(CustomerRequest request) {
        Customer customer = customerMapper.toEntity(request);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toResponse(savedCustomer);
    }

    @Override
    public void deleteCustomerById(Integer id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Optional<CustomerResponse> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(customerMapper::toResponse);
    }

    @Override
    public List<CustomerResponse> getCustomersByName(String name) {
        return customerRepository.findByName(name)
                .stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerResponse> getCustomersByAge(Integer age) {
        return customerRepository.findByAge(age)
                .stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());
    }


    //NOTAS SOBRE OPTIONAL : no te devuelve el valor sino un Optional. por eso teno que hacer el orElseThrow ( devuelve el valor si existe y sino manda un NoSuchElementExceptio)
    // en este caso mandamos un error personalizado.
    @Override
    public CustomerResponse modifyCustomerEmail(Integer id, String newEmail){
        Optional<Customer> customer = customerRepository.findById(id);
        Customer modifiedCustomer = customer.orElseThrow(() -> new RuntimeException("Customer with  " + id + " does not exist."));
        modifiedCustomer.setEmail(newEmail);
        Customer savedCustomer = customerRepository.save(modifiedCustomer);
        return customerMapper.toResponse(savedCustomer);
    }

    @Override
    public CustomerResponse modifyCustomer(Integer id, Map<String, Object> updates) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer with  " + id + " does not exist."));

        if (updates == null || updates.isEmpty()) {
            return customerMapper.toResponse(customer);
        }

        Customer updatedCustomer = JsonHelper.mergeJson(customer, updates, Customer.class);

        Customer savedCustomer = customerRepository.save(updatedCustomer);
        return customerMapper.toResponse(savedCustomer);
    }
}

// NOTAS SOBRE STREAM:
// Se pone a partir de Java 8, es una secuencia de elementos de una colección sobre la que aplicas
// operaciones funcionales ( filtrar, ordenar, reducir, trasnformar ). NO se modifica la colección ( es decir, los streams son INMUTABLES ).
// original.
// Sustituye a los for basicamente for ( Customer customer : customers )
// Más eficiente, en paralelo con el parallelStream
//
// Tiene operaciones INTERMEDIAS (LAZY) que no se ejecutan hasta una operación terminal ->

// map() → Transforma cada elemento.
// filter() → Filtra elementos según condición.
// sorted() → Ordena elementos.
// distinct() → Elimina duplicados.
// limit(n) → Toma los primeros n elementos.
// skip(n) → Salta los primeros n elementos.
//
// Operaciones TERMINALS (inician el stream) ->

// collect(Collectors.toList()) → Convierte a lista.
// forEach() → Itera sobre los elementos.
// count() → Cuenta elementos.
// reduce() → Reduce elementos a un solo valor.
// findFirst() → Obtiene el primer elemento.
// findAny() → Obtiene cualquier elemento.


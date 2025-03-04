package com.nikols.controllers;

import com.nikols.models.entities.Customer;
import com.nikols.models.requests.CustomerRequest;
import com.nikols.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController //Esto hace que to do lo que este anotado con @GetMapping, @Post etc sean cosas el usuario pueda acceder
@RequestMapping("api/v1/customers") //Como está al root level, cada vez que alguien vaya a este link, se llama al GetMapping y recibirá un empty List por [3]

public class CustomerController {

    private final CustomerService customerService;

    // private -> solo se puede acceder dentro de la clase donde esté
    // final -> si se asigna un valor a customerRepository, no se puede cambiar, i.e., no se puede reasignar a otro CustomerRepository
    // aquí instanciamos CustomerService

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //Recuerda que aquí lo que ocurre es que Main recibe un objeto CustomerRepository como PARÁMETRO
    //Luego asigna a nuestro CAMPO customerRepository de la clase el parámetro.
    //Esto lo hacemos porque Spring Boot necesita INYECTAR la instancia de CustomerRepository en Main (equivalente a hacer un @Autowired).
    //INYECTAR -> El framework crea automáticamente un objeto y lo proporciona a las partes del código que lo necesitan.

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();//[3]
    }

    @GetMapping("/{id}")
    public Customer findCustomerById(@PathVariable("id") Integer id){
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public Customer createCustomer(@Valid @RequestBody CustomerRequest request){
        return customerService.saveCustomer(request);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") Integer id){
        customerService.deleteCustomerById(id);
    }

    @GetMapping("/{email}")
    public Optional<Customer> findCustomerByEmail(@PathVariable("email") String email){
        return customerService.getCustomerByEmail(email);
    }

    @GetMapping("/{name}")
    public List<Customer> findCustomersByName(@PathVariable("name") String name){
        return customerService.getCustomersByName(name);
    }

    @GetMapping("/{age}")
    public List<Customer> findCustomersByAge(@PathVariable("age") Integer age){
        return customerService.getCustomersByAge(age);
    }

}

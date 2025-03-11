package com.nikols.controllers;

import com.nikols.models.requests.PaymentRequest;
import com.nikols.models.responses.CustomerResponse;
import com.nikols.models.responses.PaymentResponse;
import com.nikols.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<PaymentResponse> getAllPayments(){
        return paymentService.getAllPayments();
    }

    @PostMapping
    public PaymentResponse createPayment(PaymentRequest request){
        return paymentService.createPayment(request);
    }

    @GetMapping("/id/{id}")
    public PaymentResponse getPaymentById(@PathVariable Integer id){
        return paymentService.getPaymentById(id);
    }

    @GetMapping("/associated-customer/{id}")
    public CustomerResponse getCustomerFromPayment(@PathVariable Integer id){
        return paymentService.getCustomerFromPayment(id);
    }

    @GetMapping("/card-type/{cardType}")
    public List<PaymentResponse> getAllCardTypes(@PathVariable String cardType){
        return paymentService.getAllCardTypes(cardType);
    }

    @GetMapping("/expiration-date/{expirationDate}")
    public PaymentResponse getPaymentByExpirationDate(@PathVariable String expirationDate){
        return paymentService.getPaymentByExpirationDate(expirationDate);
    }

    @PatchMapping("/modify-payment/{id}")
    public PaymentResponse modifyPayment(@PathVariable Integer id, @RequestBody Map<String, Object> updates){
        return paymentService.modifyPayment(id, updates);
    }

    @GetMapping("/payments-from-customer/{customerId}")
    public List<PaymentResponse> getPaymentsFromCustomer(@PathVariable Integer customerId){
        return paymentService.getPaymentsFromCustomer(customerId);
    }


    @DeleteMapping("/{id}")
    public void deleteCardById(@PathVariable Integer id){
        paymentService.deleteCardById(id);
    }
}

package com.nikols.service;

import com.nikols.mapper.CustomerMapper;
import com.nikols.mapper.PaymentMapper;
import com.nikols.models.entities.Customer;
import com.nikols.models.entities.Payment;
import com.nikols.models.requests.PaymentRequest;
import com.nikols.models.responses.CustomerResponse;
import com.nikols.models.responses.PaymentResponse;
import com.nikols.repositories.CustomerRepository;
import com.nikols.repositories.PaymentRepository;
import com.nikols.utils.JsonHelper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;
    private final PaymentMapper paymentMapper;
    private final CustomerMapper customerMapper;


    public PaymentServiceImpl(PaymentRepository paymentRepository, CustomerRepository customerRepository, PaymentMapper paymentMapper, CustomerMapper customerMapper) {
        this.paymentRepository = paymentRepository;
        this.customerRepository = customerRepository;
        this.paymentMapper = paymentMapper;
        this.customerMapper = customerMapper;
    }

    @Override
    @Transactional
    public List<PaymentResponse> getAllPayments(){
        return paymentRepository.findAll().
                stream().
                map(paymentMapper::toResponse).
                collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PaymentResponse getPaymentById(Integer id){
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment with id: " + id + " not found."));
        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional
    public PaymentResponse createPayment(PaymentRequest request){
        Payment payment = paymentMapper.toEntity(request);
        Integer customerId = payment.getCustomerId();
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null.");
        }
        customerRepository
            .findById(customerId)
            .orElseThrow(
                () -> new IllegalArgumentException("Customer with id: " + customerId + " not found."));
        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toResponse(savedPayment);
    }

    @Override
    @Transactional
    public CustomerResponse getCustomerFromPayment(Integer id){
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment with id: " + id + " not found."));
        Integer customerId = payment.getCustomerId();
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer with id: " + customerId + " not found."));

        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional
    public List<PaymentResponse> getAllCardTypes(String cardType){
        return paymentRepository.findByCardType(cardType)
                .stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public PaymentResponse getPaymentByExpirationDate(String expirationDate){
        Payment payment = paymentRepository.findByExpirationDate(expirationDate)
                .orElseThrow(() -> new RuntimeException("Payment with expiration date: " + expirationDate + " not found."));
        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional
    public PaymentResponse modifyPayment(Integer id, Map<String, Object> updates){
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment with id: " + id + " not found."));

        if (updates == null || updates.isEmpty()) {
            return paymentMapper.toResponse(payment);
        }

        JsonHelper.mergeJsonIntoObject(payment, updates);

        paymentRepository.save(payment);

        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional
    public List<PaymentResponse> getPaymentsFromCustomer(Integer customerId){
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null.");
        }
        customerRepository
                .findById(customerId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Customer with id: " + customerId + " not found."));
        return paymentRepository.findByCustomerId(customerId)
                .stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteCardById(Integer id){
        paymentRepository.deleteById(id);
    }

}

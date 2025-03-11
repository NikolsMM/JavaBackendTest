package com.nikols.service;

import com.nikols.models.requests.PaymentRequest;
import com.nikols.models.responses.CustomerResponse;
import com.nikols.models.responses.PaymentResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface PaymentService {
    List<PaymentResponse> getAllPayments();
    PaymentResponse createPayment(PaymentRequest request);
    PaymentResponse getPaymentById(Integer id);
    CustomerResponse getCustomerFromPayment(Integer id);
    List<PaymentResponse> getAllCardTypes(String cardType);
    PaymentResponse getPaymentByExpirationDate(String expirationDate);
    PaymentResponse modifyPayment(Integer id, Map<String, Object> updates);
    List<PaymentResponse> getPaymentsFromCustomer(Integer customerId);
    void deleteCardById(Integer id);
}

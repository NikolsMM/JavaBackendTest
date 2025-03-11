package com.nikols.repositories;

import com.nikols.models.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>{
    List<Payment> findByCardType(String cardType);
    Optional<Payment> findByExpirationDate(String expirationDate);
    List<Payment> findByCustomerId(Integer customerId);
}

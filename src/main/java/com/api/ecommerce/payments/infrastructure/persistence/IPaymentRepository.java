package com.api.ecommerce.payments.infrastructure.persistence;

import com.api.ecommerce.payments.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment,Long> {
}
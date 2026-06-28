package com.daksh.Repository;

import com.daksh.Model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {

PaymentOrder findByPaymentLinkId(String paymentLinkId);



}

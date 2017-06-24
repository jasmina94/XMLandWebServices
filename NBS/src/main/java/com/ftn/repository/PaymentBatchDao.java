package com.ftn.repository;

import com.ftn.model.database.PaymentBatch;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Alex on 6/24/17.
 */
public interface PaymentBatchDao extends JpaRepository<PaymentBatch, Long> {
}

package com.ftn.repository;

import com.ftn.model.database.PaymentBatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Alex on 6/24/17.
 */
public interface PaymentBatchDao extends JpaRepository<PaymentBatch, Long> {

    List<PaymentBatch> findByClearedFalse();
}

package com.ftn.service.implementation;

import com.ftn.model.dto.mt102.Mt102;
import com.ftn.repository.BankDao;
import com.ftn.repository.PaymentBatchDao;
import com.ftn.repository.PaymentDao;
import com.ftn.service.Mt102Service;
import com.ftn.service.Mt900Service;
import com.ftn.service.Mt910Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

/**
 * Created by Alex on 6/24/17.
 */
@Service
public class Mt102ServiceImpl extends WebServiceGatewaySupport implements Mt102Service {

    private final BankDao bankDao;

    private final PaymentBatchDao paymentBatchDao;

    private final PaymentDao paymentDao;

    private final Mt900Service mt900Service;

    private final Mt910Service mt910Service;

    @Autowired
    public Mt102ServiceImpl(BankDao bankDao, PaymentBatchDao paymentBatchDao, PaymentDao paymentDao, Mt900Service mt900Service, Mt910Service mt910Service) {
        this.bankDao = bankDao;
        this.paymentBatchDao = paymentBatchDao;
        this.paymentDao = paymentDao;
        this.mt900Service = mt900Service;
        this.mt910Service = mt910Service;
    }

    @Override
    public void process(Mt102 mt102) {

    }
}

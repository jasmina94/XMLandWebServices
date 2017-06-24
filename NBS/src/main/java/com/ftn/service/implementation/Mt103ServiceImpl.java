package com.ftn.service.implementation;

import com.ftn.exception.ServiceFaultException;
import com.ftn.model.Mt103;
import com.ftn.model.TPodaciBanka;
import com.ftn.model.error.ServiceFault;
import com.ftn.repository.BankDao;
import com.ftn.service.Mt103Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Alex on 6/22/17.
 */
@Service
public class Mt103ServiceImpl implements Mt103Service {

    @Autowired
    private
    BankDao bankDao;

    @Override
    public Mt103 process(Mt103 mt103) {

        final String creditorBankSwiftCode = mt103.getPodaciOPoveriocu().getPodaciOBanci().getSwiftKod();
        final String debtorBankSwiftCode = mt103.getPodaciODuzniku().getPodaciOBanci().getSwiftKod();

        final TPodaciBanka creditorBank = bankDao.findBySwiftKod(creditorBankSwiftCode).orElseThrow(() ->
                new ServiceFaultException("Not found.", new ServiceFault("404", "No bank with swift code " + creditorBankSwiftCode + ".")));
        final TPodaciBanka debtorBank = bankDao.findBySwiftKod(debtorBankSwiftCode).orElseThrow(() ->
                new ServiceFaultException("Not found.", new ServiceFault("404", "No bank with swift code " + debtorBankSwiftCode + ".")));

        return mt103;
    }
}

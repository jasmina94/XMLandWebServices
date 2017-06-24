package com.ftn.service.implementation;

import com.ftn.exception.ServiceFaultException;
import com.ftn.model.database.Bank;
import com.ftn.model.dto.mt103.Mt103;
import com.ftn.model.dto.error.ServiceFault;
import com.ftn.model.dto.mt900.Mt900;
import com.ftn.model.dto.types.TPodaciBanka;
import com.ftn.repository.BankDao;
import com.ftn.service.Mt103Service;
import com.ftn.service.Mt900Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Alex on 6/22/17.
 */
@Service
public class Mt103ServiceImpl implements Mt103Service {

    @Autowired
    private BankDao bankDao;

    @Autowired
    private Mt900Service mt900Service;

    @Override
    public Mt103 process(Mt103 mt103) {

        final String creditorBankSwiftCode = mt103.getPodaciOPoveriocu().getPodaciOBanci().getSwiftKod();
        final String debtorBankSwiftCode = mt103.getPodaciODuzniku().getPodaciOBanci().getSwiftKod();

        final Bank creditorBank = bankDao.findBySwiftCode(creditorBankSwiftCode).orElseThrow(() ->
                new ServiceFaultException("Not found.", new ServiceFault("404", "No bank with swift code " + creditorBankSwiftCode + ".")));
        final Bank debtorBank = bankDao.findBySwiftCode(debtorBankSwiftCode).orElseThrow(() ->
                new ServiceFaultException("Not found.", new ServiceFault("404", "No bank with swift code " + debtorBankSwiftCode + ".")));

        // Calculate and save balance changes on both banks

        creditorBank.setAccountBalance(creditorBank.getAccountBalance() - mt103.getPodaciOUplati().getIznos().getValue().doubleValue());
        bankDao.save(creditorBank);

        debtorBank.setAccountBalance(debtorBank.getAccountBalance() + mt103.getPodaciOUplati().getIznos().getValue().doubleValue());
        bankDao.save(debtorBank);

        // Send Mt900

        mt900Service.send(mt103);

        return mt103;
    }
}

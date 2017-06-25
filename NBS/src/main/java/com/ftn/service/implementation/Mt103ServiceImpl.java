package com.ftn.service.implementation;

import com.ftn.exception.ServiceFaultException;
import com.ftn.model.database.Bank;
import com.ftn.model.dto.mt103.GetMt103Request;
import com.ftn.model.dto.mt103.GetMt103Response;
import com.ftn.model.dto.mt103.Mt103;
import com.ftn.model.dto.error.ServiceFault;
import com.ftn.model.dto.mt910.Mt910;
import com.ftn.repository.BankDao;
import com.ftn.service.Mt103Service;
import com.ftn.service.Mt900Service;
import com.ftn.service.Mt910Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

/**
 * Created by Alex on 6/22/17.
 */
@Service
public class Mt103ServiceImpl extends WebServiceGatewaySupport implements Mt103Service {

    @Autowired
    private BankDao bankDao;

    @Autowired
    private Mt900Service mt900Service;

    @Autowired
    private Mt910Service mt910Service;

    @Override
    public void process(Mt103 mt103) {

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

        // Forward Mt103
        send(mt103);

        // Send Mt910
        mt910Service.send(mt103);
    }

    private void send(Mt103 mt103) {

        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(GetMt103Request.class, GetMt103Response.class);
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);

        final GetMt103Request getMt103Request = new GetMt103Request();
        getMt103Request.setMt103(mt103);
        final String swiftCode = mt103.getPodaciOPoveriocu().getPodaciOBanci().getSwiftKod();
        final Bank creditorBank = bankDao.findBySwiftCode(swiftCode).orElseThrow(() ->
                new ServiceFaultException("Not found.", new ServiceFault("404", "No bank with swift code " + swiftCode + ".")));
        final GetMt103Response response = (GetMt103Response) getWebServiceTemplate().marshalSendAndReceive(creditorBank.getUrl(), getMt103Request);
        // TODO: Based on response throw an exception maybe?
    }
}

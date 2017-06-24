package com.ftn.service.implementation;

import com.ftn.exception.ServiceFaultException;
import com.ftn.model.database.Bank;
import com.ftn.model.dto.error.ServiceFault;
import com.ftn.model.dto.mt103.Mt103;
import com.ftn.model.dto.mt900.GetMt900Request;
import com.ftn.model.dto.mt900.GetMt900Response;
import com.ftn.model.dto.mt900.Mt900;
import com.ftn.model.dto.types.TOznakaValute;
import com.ftn.model.dto.types.TPodaciBanka;
import com.ftn.model.dto.types.TPodaciNalog;
import com.ftn.repository.BankDao;
import com.ftn.service.Mt900Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

/**
 * Created by Alex on 6/24/17.
 */
@Service
public class Mt900ServiceImpl extends WebServiceGatewaySupport implements Mt900Service {

    @Autowired
    private BankDao bankDao;

    @Override
    public void send(Mt103 mt103) {

        final Mt900 mt900 = new Mt900();

        final TPodaciBanka debtorsBank = new TPodaciBanka();
        debtorsBank.setObracunskiRacun(mt103.getPodaciODuzniku().getBrojRacuna());
        debtorsBank.setSwiftKod(mt103.getPodaciODuzniku().getPodaciOBanci().getSwiftKod());

        mt900.setPodaciOBanciDuznika(debtorsBank);
        mt900.setIdPoruke(mt103.getIdPoruke());

        final TPodaciNalog.Iznos amount = new TPodaciNalog.Iznos();
        amount.setValue(mt103.getPodaciOUplati().getIznos().getValue());
        amount.setValuta(TOznakaValute.valueOf(mt103.getPodaciOUplati().getIznos().getValuta()));

        final Mt900.PodaciONalogu paymentRequest = new Mt900.PodaciONalogu();
        paymentRequest.setIdPorukeNaloga(mt103.getIdPoruke());
        paymentRequest.setDatumValute(mt103.getPodaciOUplati().getDatumValute());
        paymentRequest.setIznos(amount);

        mt900.setPodaciONalogu(paymentRequest);

        send(mt900);
    }

    private void send(Mt900 mt900) {

        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(GetMt900Request.class, GetMt900Response.class);
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);

        GetMt900Request getMt900Request = new GetMt900Request();
        getMt900Request.setMt900(mt900);
        final String swiftCode = mt900.getPodaciOBanciDuznika().getSwiftKod();
        final Bank debtorBank = bankDao.findBySwiftCode(swiftCode).orElseThrow(() ->
                new ServiceFaultException("Not found.", new ServiceFault("404", "No bank with swift code " + swiftCode + ".")));
        final Object o = getWebServiceTemplate().marshalSendAndReceive(debtorBank.getUrl(), getMt900Request);
        GetMt900Response response = (GetMt900Response) o;
    }
}

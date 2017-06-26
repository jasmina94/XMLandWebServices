package com.ftn.service.implementation;

import com.ftn.exception.ServiceFaultException;
import com.ftn.model.database.Bank;
import com.ftn.model.dto.error.ServiceFault;
import com.ftn.model.dto.mt102.Mt102;
import com.ftn.model.dto.mt103.Mt103;
import com.ftn.model.dto.mt910.GetMt910Request;
import com.ftn.model.dto.mt910.GetMt910Response;
import com.ftn.model.dto.mt910.Mt910;
import com.ftn.model.dto.types.TOznakaValute;
import com.ftn.model.dto.types.TPodaciBanka;
import com.ftn.model.dto.types.TPodaciNalog;
import com.ftn.repository.BankDao;
import com.ftn.service.Mt910Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

/**
 * Created by Alex on 6/24/17.
 */
@Service
public class Mt910ServiceImpl extends WebServiceGatewaySupport implements Mt910Service {

    @Autowired
    private BankDao bankDao;

    @Override
    public void send(Mt103 mt103) {

        final Mt910 mt910 = new Mt910();

        mt910.setPodaciOBanciPoverioca(mt103.getPodaciOPoveriocu().getPodaciOBanci());
        mt910.setIdPoruke(mt103.getIdPoruke());

        final TPodaciNalog.Iznos amount = new TPodaciNalog.Iznos();
        amount.setValue(mt103.getPodaciOUplati().getIznos().getValue());
        amount.setValuta(TOznakaValute.valueOf(mt103.getPodaciOUplati().getIznos().getValuta()));

        final Mt910.PodaciONalogu paymentRequest = new Mt910.PodaciONalogu();
        paymentRequest.setIdPorukeNaloga("Mt103|" + mt103.getIdPoruke());
        paymentRequest.setDatumValute(mt103.getPodaciOUplati().getDatumValute());
        paymentRequest.setIznos(amount);

        mt910.setPodaciONalogu(paymentRequest);

        send(mt910);
    }

    @Override
    public void send(Mt102 mt102) {

        final Mt910 mt910 = new Mt910();

        mt910.setPodaciOBanciPoverioca(mt102.getMt102Zaglavlje().getPodaciOBanciPoverioca());
        mt910.setIdPoruke(mt102.getMt102Zaglavlje().getIdPoruke());

        final TPodaciNalog.Iznos amount = new TPodaciNalog.Iznos();
        amount.setValue(mt102.getMt102Zaglavlje().getUkupanIznos());
        amount.setValuta(mt102.getMt102Zaglavlje().getSifraValute());

        final Mt910.PodaciONalogu paymentRequest = new Mt910.PodaciONalogu();
        paymentRequest.setIdPorukeNaloga("Mt102|" + mt102.getMt102Zaglavlje().getIdPoruke());
        paymentRequest.setDatumValute(mt102.getMt102Zaglavlje().getDatumValute());
        paymentRequest.setIznos(amount);

        mt910.setPodaciONalogu(paymentRequest);

        send(mt910);
    }

    private void send(Mt910 mt910) {

        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(GetMt910Request.class, GetMt910Response.class);
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);

        GetMt910Request getMt910Request = new GetMt910Request();
        getMt910Request.setMt910(mt910);
        final String swiftCode = mt910.getPodaciOBanciPoverioca().getSwiftKod();
        final Bank creditorBank = bankDao.findBySwiftCode(swiftCode).orElseThrow(() ->
                new ServiceFaultException("Not found.", new ServiceFault("404", "No bank with swift code " + swiftCode + ".")));
        final GetMt910Response response = (GetMt910Response) getWebServiceTemplate().marshalSendAndReceive(creditorBank.getUrl(), getMt910Request);
        // TODO: Based on response throw an exception maybe?
    }
}

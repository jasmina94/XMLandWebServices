package com.ftn.service.implementation;

import com.ftn.exception.ServiceFaultException;
import com.ftn.model.database.Bank;
import com.ftn.model.database.Payment;
import com.ftn.model.database.PaymentBatch;
import com.ftn.model.dto.error.ServiceFault;
import com.ftn.model.dto.mt102.GetMt102Request;
import com.ftn.model.dto.mt102.GetMt102Response;
import com.ftn.model.dto.mt102.Mt102;
import com.ftn.repository.BankDao;
import com.ftn.repository.PaymentBatchDao;
import com.ftn.repository.PaymentDao;
import com.ftn.service.Mt102Service;
import com.ftn.service.Mt900Service;
import com.ftn.service.Mt910Service;
import com.ftn.util.XMLGregorianCalendarConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

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

    private final Gson gson;

    @Autowired
    public Mt102ServiceImpl(BankDao bankDao, PaymentBatchDao paymentBatchDao, PaymentDao paymentDao, Mt900Service mt900Service, Mt910Service mt910Service) {
        this.bankDao = bankDao;
        this.paymentBatchDao = paymentBatchDao;
        this.paymentDao = paymentDao;
        this.mt900Service = mt900Service;
        this.mt910Service = mt910Service;

        final GsonBuilder gson_builder = new GsonBuilder();
        gson_builder.registerTypeAdapter(XMLGregorianCalendar.class,
                new XMLGregorianCalendarConverter.Serializer());
        gson_builder.registerTypeAdapter(XMLGregorianCalendar.class,
                new XMLGregorianCalendarConverter.Deserializer());
        this.gson = gson_builder.create();
    }

    @Override
    public void process(Mt102 mt102) {

        final String creditorBankSwiftCode = mt102.getMt102Zaglavlje().getPodaciOBanciPoverioca().getSwiftKod();
        final String debtorBankSwiftCode = mt102.getMt102Zaglavlje().getPodaciOBanciDuznika().getSwiftKod();

        // Check if banks in question exist

        bankDao.findBySwiftCode(creditorBankSwiftCode).orElseThrow(() ->
                new ServiceFaultException("Not found.", new ServiceFault("404", "No bank with swift code " + creditorBankSwiftCode + ".")));
        bankDao.findBySwiftCode(debtorBankSwiftCode).orElseThrow(() ->
                new ServiceFaultException("Not found.", new ServiceFault("404", "No bank with swift code " + debtorBankSwiftCode + ".")));

        // Save payment batch

        final PaymentBatch paymentBatch = new PaymentBatch();
        paymentBatch.setMessageId(mt102.getMt102Zaglavlje().getIdPoruke());
        paymentBatch.setCreditorAccountNumber(mt102.getMt102Zaglavlje().getPodaciOBanciPoverioca().getObracunskiRacun());
        paymentBatch.setCreditorSwift(mt102.getMt102Zaglavlje().getPodaciOBanciPoverioca().getSwiftKod());
        paymentBatch.setDebtorAccountNumber(mt102.getMt102Zaglavlje().getPodaciOBanciDuznika().getObracunskiRacun());
        paymentBatch.setDebtorSwift(mt102.getMt102Zaglavlje().getPodaciOBanciDuznika().getSwiftKod());
        paymentBatch.setCurrency(mt102.getMt102Zaglavlje().getSifraValute().value());
        paymentBatch.setDateOfValue(mt102.getMt102Zaglavlje().getDatumValute().toGregorianCalendar().getTime());
        paymentBatch.setDateOfPayment(mt102.getMt102Zaglavlje().getDatum().toGregorianCalendar().getTime());
        paymentBatch.setTotal(mt102.getMt102Zaglavlje().getUkupanIznos().doubleValue());
        paymentBatch.setMt102Model(gson.toJson(mt102));
        paymentBatch.setCleared(false);
        paymentBatchDao.save(paymentBatch);

        // Save payments

        mt102.getMt102Telo().forEach(paymentDTO -> {
            final Payment payment = new Payment();
            payment.setCreditorAccountNumber(paymentDTO.getPodaciOPoveriocu().getBrojRacuna());
            payment.setDebtorAccountNumber(paymentDTO.getPodaciODuzniku().getBrojRacuna());
            payment.setTotal(paymentDTO.getIznos().getValue().doubleValue());
            payment.setCurrency(paymentDTO.getIznos().getValuta().value());
            payment.setDateOfOrder(paymentDTO.getDatumNaloga().toGregorianCalendar().getTime());
            payment.setPaymentPurpose(paymentDTO.getSvrhaPlacanja());
            payment.setPaymentBatch(paymentBatch);
            paymentDao.save(payment);
        });

        final List<PaymentBatch> clearingCandidates = paymentBatchDao.findByClearedFalse();
        if (clearingCandidates.size() > 0) {
            clear(clearingCandidates);
        }
    }

    private void clear(List<PaymentBatch> clearingCandidates) {

        clearingCandidates.forEach(paymentBatch -> {

            final Bank creditorBank = bankDao.findBySwiftCode(paymentBatch.getCreditorSwift()).orElseThrow(() ->
                    new ServiceFaultException("Not found.", new ServiceFault("404", "No bank with swift code " + paymentBatch.getCreditorSwift() + ".")));
            final Bank debtorBank = bankDao.findBySwiftCode(paymentBatch.getDebtorSwift()).orElseThrow(() ->
                    new ServiceFaultException("Not found.", new ServiceFault("404", "No bank with swift code " + paymentBatch.getDebtorSwift() + ".")));

            creditorBank.setAccountBalance(creditorBank.getAccountBalance() - paymentBatch.getTotal());
            bankDao.save(creditorBank);

            debtorBank.setAccountBalance(debtorBank.getAccountBalance() + paymentBatch.getTotal());
            bankDao.save(debtorBank);

            final Mt102 mt102 = gson.fromJson(paymentBatch.getMt102Model(), Mt102.class);

            // Send Mt900
            mt900Service.send(mt102);

            // Forward Mt102
            send(mt102);

            // Send Mt910
            mt910Service.send(mt102);

            paymentBatch.setCleared(true);
            paymentBatchDao.save(paymentBatch);
        });
    }

    private void send(Mt102 mt102) {

        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(GetMt102Request.class, GetMt102Response.class);
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);

        final GetMt102Request getMt102Request = new GetMt102Request();
        getMt102Request.setMt102(mt102);
        final String swiftCode = mt102.getMt102Zaglavlje().getPodaciOBanciPoverioca().getSwiftKod();
        final Bank creditorBank = bankDao.findBySwiftCode(swiftCode).orElseThrow(() ->
                new ServiceFaultException("Not found.", new ServiceFault("404", "No bank with swift code " + swiftCode + ".")));
        final GetMt102Response response = (GetMt102Response) getWebServiceTemplate().marshalSendAndReceive(creditorBank.getUrl(), getMt102Request);
        // TODO: Based on response throw an exception maybe?
    }
}

package com.ftn.service.implementation;

import com.ftn.exception.ServiceFaultException;
import com.ftn.model.database.Bank;
import com.ftn.model.database.Payment;
import com.ftn.model.database.PaymentBatch;
import com.ftn.model.dto.error.ServiceFault;
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
        paymentBatch.setMt102Model(new Gson().toJson(mt102));
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
        if (clearingCandidates.size() > 1) {
            clear(clearingCandidates);
        }
    }

    private void clear(List<PaymentBatch> clearingCandidates) {

        final GsonBuilder gson_builder = new GsonBuilder();
        gson_builder.registerTypeAdapter(XMLGregorianCalendar.class,
                new XMLGregorianCalendarConverter.Serializer());
        gson_builder.registerTypeAdapter(XMLGregorianCalendar.class,
                new XMLGregorianCalendarConverter.Deserializer());
        final Gson gson = gson_builder.create();

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

            mt900Service.send(mt102);
        });
    }
}

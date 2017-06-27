package rs.ac.uns.ftn.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import rs.ac.uns.ftn.exception.ServiceFaultException;
import rs.ac.uns.ftn.model.database.*;
import rs.ac.uns.ftn.model.dto.error.ServiceFault;
import rs.ac.uns.ftn.model.dto.mt102.GetMt102Request;
import rs.ac.uns.ftn.model.dto.mt102.GetMt102Response;
import rs.ac.uns.ftn.model.dto.mt102.Mt102;
import rs.ac.uns.ftn.model.dto.mt102body.Mt102Telo;
import rs.ac.uns.ftn.model.dto.mt102header.Mt102Zaglavlje;
import rs.ac.uns.ftn.model.dto.mt900.Mt900;
import rs.ac.uns.ftn.model.dto.mt910.Mt910;
import rs.ac.uns.ftn.model.dto.tipovi.TOznakaValute;
import rs.ac.uns.ftn.model.dto.tipovi.TPodaciBanka;
import rs.ac.uns.ftn.model.dto.tipovi.TPodaciPlacanje;
import rs.ac.uns.ftn.model.dto.tipovi.TPravnoLice;
import rs.ac.uns.ftn.model.environment.EnvironmentProperties;
import rs.ac.uns.ftn.repository.*;
import rs.ac.uns.ftn.service.ClearingService;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by zlatan on 6/25/17.
 */
@Service
public class ClearingServiceImpl extends WebServiceGatewaySupport implements ClearingService {

    @Autowired
    private EnvironmentProperties environmentProperties;

    @Autowired
    private Mt102Repository mt102Repository;

    @Autowired
    private RacunRepository racunRepository;

    @Autowired
    private DnevnoStanjeRacunaRepository dnevnoStanjeRacunaRepository;

    @Autowired
    private AnalitikaIzvodaRepository analitikaIzvodaRepository;

    @Autowired
    private PojedinacniNalogZaPlacanjeRepository pojedinacniNalogZaPlacanjeRepository;

    @Override
    public Mt102 createMT102(Mt102Model mt102Model) {
        Mt102 mt102 = new Mt102();
        Mt102Zaglavlje zaglavlje = new Mt102Zaglavlje();

        TPodaciBanka bankaDuznika = new TPodaciBanka();
        bankaDuznika.setSwiftKod(mt102Model.getSwiftBankeDuznika());
        bankaDuznika.setObracunskiRacun(mt102Model.getRacunBankeDuznika());

        TPodaciBanka bankaPoverioca = new TPodaciBanka();
        bankaPoverioca.setSwiftKod(mt102Model.getSwiftBankePoverioca());
        bankaPoverioca.setObracunskiRacun(mt102Model.getRacunBankePoverioca());

        zaglavlje.setPodaciOBanciDuznika(bankaDuznika);
        zaglavlje.setPodaciOBanciPoverioca(bankaPoverioca);
        zaglavlje.setUkupanIznos(BigDecimal.valueOf(mt102Model.getUkupanIznos()));
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(mt102Model.getDatumValute());
        try {
            zaglavlje.setDatumValute(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        calendar.setTime(mt102Model.getDatumNaloga());
        try {
            zaglavlje.setDatum(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        zaglavlje.setSifraValute(TOznakaValute.fromValue(mt102Model.getSifraValute()));
        zaglavlje.setIdPoruke(mt102Model.getIdPoruke());
        mt102.setMt102Zaglavlje(zaglavlje);

        //Pojedinacni nalozi
        for (PojedinacniNalogZaPlacanje pnzp: mt102Model.getListaNalogaZaPlacanje()){
            Mt102Telo telo = new Mt102Telo();

            TPravnoLice duznik = new TPravnoLice();
            duznik.setNaziv(pnzp.getDuznik());
            duznik.setBrojRacuna(pnzp.getRacunDuznika());
            telo.setPodaciODuzniku(duznik);

            TPravnoLice poverilac = new TPravnoLice();
            poverilac.setNaziv(pnzp.getPoverilac());
            poverilac.setBrojRacuna(pnzp.getRacunPoverioca());
            telo.setPodaciOPoveriocu(poverilac);

            Mt102Telo.Iznos iznos = new Mt102Telo.Iznos();
            iznos.setValue(BigDecimal.valueOf(pnzp.getIznos()));
            iznos.setValuta(TOznakaValute.valueOf(pnzp.getSifraValute()));
            telo.setIznos(iznos);

            TPodaciPlacanje podaciZaduzenje = new TPodaciPlacanje();
            podaciZaduzenje.setModel(BigInteger.valueOf(pnzp.getModelZaduzenja()));
            podaciZaduzenje.setPozivNaBroj(pnzp.getPozivNaBrojZaduzenja());
            telo.setPodaciOZaduzenju(podaciZaduzenje);

            TPodaciPlacanje podaciOdobrenje = new TPodaciPlacanje();
            podaciOdobrenje.setModel(BigInteger.valueOf(pnzp.getModelOdobrenja()));
            podaciOdobrenje.setPozivNaBroj(pnzp.getPozivNaBrojOdobrenja());
            telo.setPodaciOOdobrenju(podaciOdobrenje);

            GregorianCalendar calendarNew = new GregorianCalendar();
            calendarNew.setTime(pnzp.getDatumNaloga());
            try {
                telo.setDatumNaloga(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendarNew));
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }

            telo.setIdNaloga(pnzp.getIdNaloga());
            telo.setSvrhaPlacanja(pnzp.getSvrhaPlacanja());

            mt102.getMt102Telo().add(telo);
        }
        return mt102;
    }

    @Override
    public void sendMT102(Mt102 mt102) {
        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(GetMt102Request.class, GetMt102Response.class);
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);

        final GetMt102Request getMt102Request = new GetMt102Request();
        getMt102Request.setMt102(mt102);

        try{
            final GetMt102Response response = (GetMt102Response) getWebServiceTemplate()
                    .marshalSendAndReceive(environmentProperties.getNbsUrl(), getMt102Request);
        }catch (Exception e) {
            throw new ServiceFaultException("Pogresan odgovor.", new ServiceFault("500", "Nemoguce slanje Mt102 modela narodnoj banci!"));
        }
    }

    @Override
    public String processMT900(Mt900 mt900) {
        Optional<Mt102Model> mt102Model = mt102Repository.findByIdPoruke(mt900.getIdPoruke());
        if(!mt102Model.isPresent()){
            throw new ServiceFaultException("Nije pronadjeno.", new ServiceFault("404", "Nije pronadjen Mt102 model koji!"));
        }else{
            for (PojedinacniNalogZaPlacanje pnzp: mt102Model.get().getListaNalogaZaPlacanje()) {
                Optional<Racun> racunDuznika = racunRepository.findByBrojRacuna(pnzp.getRacunDuznika());
                if(!racunDuznika.isPresent()){
                    throw new ServiceFaultException("Nije pronadjeno.", new ServiceFault("404", "Nije pronadjen racun duznika!"));
                }else{
                    System.out.println("ovakav racun postoji " + racunDuznika.get().getBrojRacuna());
                    Racun racunDuznikaReal = racunDuznika.get();
                    AnalitikaIzvoda analitikaIzvoda = napraviAnalitiku(pnzp, racunDuznikaReal, true, mt102Model.get().getDatumValute());
                    evidentirajDnevnoStanje(analitikaIzvoda, racunDuznikaReal, true);
                    racunDuznikaReal.setSaldo(racunDuznikaReal.getSaldo() - racunDuznikaReal.getRezervisanaSredstva());
                    racunDuznikaReal.setRezervisanaSredstva(0.0);
                    racunRepository.save(racunDuznikaReal);
                }
            }
        }
        return "ok";
    }

    @Override
    public String processMT910(Mt910 mt910) {
        Optional<Mt102Model> mt102Model = mt102Repository.findByIdPoruke(mt910.getIdPoruke());
        if(!mt102Model.isPresent()){
            throw new ServiceFaultException("Nije pronadjen.", new ServiceFault("404", "Nije pronadjen Mt102model u banci poverioca!"));
        }else{
            for (PojedinacniNalogZaPlacanje pnzp: mt102Model.get().getListaNalogaZaPlacanje()) {
                Optional<Racun> racunPoverioca = racunRepository.findByBrojRacuna(pnzp.getRacunPoverioca());
                if(!racunPoverioca.isPresent()){
                    throw new ServiceFaultException("Nije pronadjen.", new ServiceFault("404", "Nema racuna poverioca."));
                }else{
                    Racun racunPoveriocaReal = racunPoverioca.get();
                    AnalitikaIzvoda analitikaIzvoda = napraviAnalitiku(pnzp, racunPoveriocaReal, false, mt102Model.get().getDatumValute());
                    evidentirajDnevnoStanje(analitikaIzvoda, racunPoveriocaReal, false);
                    racunPoveriocaReal.setSaldo(racunPoveriocaReal.getSaldo() + pnzp.getIznos().doubleValue());
                    racunRepository.save(racunPoveriocaReal);
                }
            }
        }
        return "ok";
    }

    @Override
    public void save(Mt102 mt102) {
        Mt102Model mt102Model = new Mt102Model();
        mt102Model.setPoslato(true);
        mt102Model.setDatumNaloga(mt102.getMt102Zaglavlje().getDatum().toGregorianCalendar().getTime());
        mt102Model.setDatumValute(mt102.getMt102Zaglavlje().getDatumValute().toGregorianCalendar().getTime());
        mt102Model.setIdPoruke(mt102.getMt102Zaglavlje().getIdPoruke());
        mt102Model.setSifraValute(mt102.getMt102Zaglavlje().getSifraValute().value());
        mt102Model.setUkupanIznos(mt102.getMt102Zaglavlje().getUkupanIznos().doubleValue());
        mt102Model.setSwiftBankeDuznika(mt102.getMt102Zaglavlje().getPodaciOBanciDuznika().getSwiftKod());
        mt102Model.setSwiftBankePoverioca(mt102.getMt102Zaglavlje().getPodaciOBanciPoverioca().getSwiftKod());
        mt102Model.setRacunBankeDuznika(mt102.getMt102Zaglavlje().getPodaciOBanciDuznika().getObracunskiRacun());
        mt102Model.setRacunBankePoverioca(mt102.getMt102Zaglavlje().getPodaciOBanciPoverioca().getObracunskiRacun());
        mt102Model = mt102Repository.save(mt102Model);
        List<PojedinacniNalogZaPlacanje> pojedinacniNalogZaPlacanjeList = new ArrayList<>();
        for (Mt102Telo telo : mt102.getMt102Telo()){
            PojedinacniNalogZaPlacanje pnzp = new PojedinacniNalogZaPlacanje();
            pnzp.setIdNaloga(telo.getIdNaloga());
            pnzp.setDuznik(telo.getPodaciODuzniku().getNaziv());
            pnzp.setPoverilac(telo.getPodaciOPoveriocu().getNaziv());
            pnzp.setSvrhaPlacanja(telo.getSvrhaPlacanja());
            pnzp.setDatumNaloga(telo.getDatumNaloga().toGregorianCalendar().getTime());
            pnzp.setRacunDuznika(telo.getPodaciODuzniku().getBrojRacuna());
            pnzp.setRacunPoverioca(telo.getPodaciOPoveriocu().getBrojRacuna());
            pnzp.setModelZaduzenja(telo.getPodaciOZaduzenju().getModel().intValue());
            pnzp.setModelOdobrenja(telo.getPodaciOOdobrenju().getModel().intValue());
            pnzp.setPozivNaBrojZaduzenja(telo.getPodaciOZaduzenju().getPozivNaBroj());
            pnzp.setPozivNaBrojOdobrenja(telo.getPodaciOOdobrenju().getPozivNaBroj());
            pnzp.setSifraValute(telo.getIznos().getValuta().value());
            pnzp.setIznos(telo.getIznos().getValue().doubleValue());
            pnzp.setMt102Model(mt102Model);
            pojedinacniNalogZaPlacanjeRepository.save(pnzp);
            pojedinacniNalogZaPlacanjeList.add(pnzp);
        }
        mt102Model.setListaNalogaZaPlacanje(pojedinacniNalogZaPlacanjeList);
        mt102Repository.save(mt102Model);
    }

    private AnalitikaIzvoda napraviAnalitiku(PojedinacniNalogZaPlacanje pnzp, Racun racunDuznik, boolean duznik, Date datumValute){
        AnalitikaIzvoda analitika = new AnalitikaIzvoda();
        analitika.setDatumNaloga(pnzp.getDatumNaloga());
        analitika.setDatumValute(datumValute);
        analitika.setPrimljeno(!duznik);
        analitika.setDuznik(pnzp.getDuznik());
        analitika.setPoverilac(pnzp.getPoverilac());
        analitika.setRacunDuznika(pnzp.getRacunDuznika());
        analitika.setRacunPoverioca(pnzp.getRacunPoverioca());
        analitika.setModelZaduzenja(pnzp.getModelZaduzenja());
        analitika.setModelOdobrenja(pnzp.getModelOdobrenja());
        analitika.setPozivNaBrojOdobrenja(pnzp.getPozivNaBrojOdobrenja());
        analitika.setPozivNaBrojZaduzenja(pnzp.getPozivNaBrojZaduzenja());
        analitika.setIznos(BigDecimal.valueOf(pnzp.getIznos()));
        analitika.setSifraValute(pnzp.getSifraValute());
        analitika.setSvrhaPlacanja(pnzp.getSvrhaPlacanja());
        return analitika;
    }

    private void evidentirajDnevnoStanje(AnalitikaIzvoda analitika, Racun racun, boolean isDuzan){
        boolean nasaoDnevnoStanje = false;
        Date datumAnalitike = analitika.getDatumNaloga();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datumAnalitike);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        datumAnalitike = calendar.getTime();
        for (DnevnoStanjeRacuna dsr : racun.getDnevnoStanjeRacuna()) {
            Date tempDatum = dsr.getDatum();
            Calendar tempCal = Calendar.getInstance();
            tempCal.setTime(tempDatum);
            tempCal.set(Calendar.MILLISECOND, 0);
            tempCal.set(Calendar.SECOND, 0);
            tempCal.set(Calendar.MINUTE, 0);
            tempCal.set(Calendar.HOUR, 0);
            tempDatum = tempCal.getTime();
            if (tempDatum.equals(datumAnalitike)){
                if(isDuzan) {
                    dsr.setUkupnoNaTeret(dsr.getUkupnoNaTeret() + analitika.getIznos().doubleValue());
                    dsr.setNovoStanje(dsr.getPredhodnoStanje() - dsr.getUkupnoNaTeret() + dsr.getUkupnoUKorist());
                    dsr.setBrojPromenaNaTeret(dsr.getBrojPromenaNaTeret() + 1);
                }else{
                    dsr.setUkupnoUKorist(dsr.getUkupnoUKorist() + analitika.getIznos().doubleValue());
                    dsr.setNovoStanje(dsr.getPredhodnoStanje() - dsr.getUkupnoNaTeret() + dsr.getUkupnoUKorist());
                    dsr.setBrojPromenaUKorist(dsr.getBrojPromenaUKorist() + 1);
                }

                dsr.getAnalitikeIzvoda().add(analitika);
                analitika.setDnevnoStanjeRacuna(dsr);
                dsr = dnevnoStanjeRacunaRepository.save(dsr);
                racun.getDnevnoStanjeRacuna().add(dsr);
                nasaoDnevnoStanje = true;
                break;
            }
        }
        if(!nasaoDnevnoStanje){
            DnevnoStanjeRacuna dnevnoStanjeRacuna = new DnevnoStanjeRacuna();
            dnevnoStanjeRacuna.setDatum(datumAnalitike);
            dnevnoStanjeRacuna.setRacun(racun);
            dnevnoStanjeRacuna.setPredhodnoStanje(racun.getSaldo());
            if(isDuzan) {
                dnevnoStanjeRacuna.setUkupnoNaTeret(analitika.getIznos().doubleValue());
                dnevnoStanjeRacuna.setNovoStanje(dnevnoStanjeRacuna.getPredhodnoStanje() - dnevnoStanjeRacuna.getUkupnoNaTeret() + dnevnoStanjeRacuna.getUkupnoUKorist());
                dnevnoStanjeRacuna.setBrojPromenaNaTeret(dnevnoStanjeRacuna.getBrojPromenaNaTeret() + 1);
            }else{
                dnevnoStanjeRacuna.setUkupnoUKorist(analitika.getIznos().doubleValue());
                dnevnoStanjeRacuna.setNovoStanje(dnevnoStanjeRacuna.getPredhodnoStanje() - dnevnoStanjeRacuna.getUkupnoNaTeret() + dnevnoStanjeRacuna.getUkupnoUKorist());
                dnevnoStanjeRacuna.setBrojPromenaUKorist(dnevnoStanjeRacuna.getBrojPromenaUKorist() + 1);
            }
            List<AnalitikaIzvoda> listaAnalitika = new ArrayList<>();
            if(dnevnoStanjeRacuna.getAnalitikeIzvoda() == null){
                listaAnalitika.add(analitika);
                dnevnoStanjeRacuna.setAnalitikeIzvoda(listaAnalitika);
            }else{
                dnevnoStanjeRacuna.getAnalitikeIzvoda().add(analitika);
            }
            dnevnoStanjeRacuna = dnevnoStanjeRacunaRepository.save(dnevnoStanjeRacuna);
            racun.getDnevnoStanjeRacuna().add(dnevnoStanjeRacuna);
            analitika.setDnevnoStanjeRacuna(dnevnoStanjeRacuna);
        }
        racunRepository.save(racun);
        analitikaIzvodaRepository.save(analitika);
    }
}

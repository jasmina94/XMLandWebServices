package rs.ac.uns.ftn.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import rs.ac.uns.ftn.model.database.*;
import rs.ac.uns.ftn.model.dto.mt102.GetMt102Request;
import rs.ac.uns.ftn.model.dto.mt102.GetMt102Response;
import rs.ac.uns.ftn.model.dto.mt102.Mt102;
import rs.ac.uns.ftn.model.dto.mt102body.Mt102Telo;
import rs.ac.uns.ftn.model.dto.mt102header.Mt102Zaglavlje;
import rs.ac.uns.ftn.model.dto.mt103.GetMt103Response;
import rs.ac.uns.ftn.model.dto.mt103.Mt103;
import rs.ac.uns.ftn.model.dto.mt900.Mt900;
import rs.ac.uns.ftn.model.dto.mt910.Mt910;
import rs.ac.uns.ftn.model.dto.tipovi.TOznakaValute;
import rs.ac.uns.ftn.model.dto.tipovi.TPodaciBanka;
import rs.ac.uns.ftn.model.dto.tipovi.TPodaciPlacanje;
import rs.ac.uns.ftn.model.dto.tipovi.TPravnoLice;
import rs.ac.uns.ftn.model.environment.EnvironmentProperties;
import rs.ac.uns.ftn.repository.AnalitikaIzvodaRepository;
import rs.ac.uns.ftn.repository.DnevnoStanjeRacunaRepository;
import rs.ac.uns.ftn.repository.Mt102Repository;
import rs.ac.uns.ftn.repository.RacunRepository;
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

        final GetMt102Response response = (GetMt102Response) getWebServiceTemplate().marshalSendAndReceive(environmentProperties.getNbsUrl(), getMt102Request);
    }

    @Override
    public String processMT900(Mt900 mt900) {
        System.out.println("Process mt900 zapoceo");
        System.out.println(mt900.getIdPoruke());

        Optional<Mt102Model> mt102Model = mt102Repository.findByIdPoruke(mt900.getIdPoruke());

        if(!mt102Model.isPresent()){
            return "Nisam pronasao model mt102Model.";
        }else{
            for (PojedinacniNalogZaPlacanje pnzp: mt102Model.get().getListaNalogaZaPlacanje()) {
                Optional<Racun> racunDuznika = racunRepository.findByBrojRacuna(pnzp.getRacunDuznika());
                if(!racunDuznika.isPresent()){
                    return "Nema racuna duznika.";
                }else{
                    System.out.println("ovakav racun postoji " + racunDuznika.get().getBrojRacuna());
                    Racun racunDuznikaReal = racunDuznika.get();
                    napraviAnalitiku(mt102Model.get(), racunDuznikaReal, true);
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
        return null;
    }

    @Override
    public void save(Mt102 mt102) {

    }

    private void napraviAnalitiku(Mt102Model mt102Model, Racun racunDuznik, boolean duznik){
        for(PojedinacniNalogZaPlacanje pnzp : mt102Model.getListaNalogaZaPlacanje()){
            AnalitikaIzvoda analitika = new AnalitikaIzvoda();
            analitika.setDatumNaloga(pnzp.getDatumNaloga());
            analitika.setDatumValute(mt102Model.getDatumValute());
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
            evidentirajDnevnoStanje(analitika, racunDuznik, duznik);
        }

    }

    private void evidentirajDnevnoStanje(AnalitikaIzvoda analitika, Racun racun, boolean isDuzan){
        //treba evidentirati dnevno stanje duznika
        boolean nasaoDnevnoStanje = false;
        Date datumAnalitike = analitika.getDatumNaloga();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datumAnalitike);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        datumAnalitike = calendar.getTime();

        //uci i pokupiti sva dnevna stanja,
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
                //nasao sam dnevno stanje
                dsr.setPredhodnoStanje(dsr.getNovoStanje());
                if(isDuzan) {
                    dsr.setNovoStanje(dsr.getNovoStanje());
                }else{
                    dsr.setNovoStanje(dsr.getNovoStanje());
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

            if(isDuzan) {
                dnevnoStanjeRacuna.setPredhodnoStanje(racun.getSaldo());
                dnevnoStanjeRacuna.setNovoStanje(racun.getSaldo() - racun.getRezervisanaSredstva());
                dnevnoStanjeRacuna.setPrometNaTeret(1);
            }else{
                dnevnoStanjeRacuna.setPredhodnoStanje(racun.getSaldo());
                dnevnoStanjeRacuna.setNovoStanje(racun.getSaldo() + racun.getRezervisanaSredstva());
                dnevnoStanjeRacuna.setPrometuKorist(1);
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

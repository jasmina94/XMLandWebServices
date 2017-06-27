package rs.ac.uns.ftn.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.exception.ServiceFaultException;
import rs.ac.uns.ftn.model.database.AnalitikaIzvoda;
import rs.ac.uns.ftn.model.database.DnevnoStanjeRacuna;
import rs.ac.uns.ftn.model.database.Racun;
import rs.ac.uns.ftn.model.dto.error.ServiceFault;
import rs.ac.uns.ftn.model.dto.presek.Presek;
import rs.ac.uns.ftn.model.dto.presek.stavkapreseka.StavkaPreseka;
import rs.ac.uns.ftn.model.dto.presek.zaglavljepreseka.ZaglavljePreseka;
import rs.ac.uns.ftn.model.dto.tipovi.TPodaciPlacanje;
import rs.ac.uns.ftn.model.dto.tipovi.TPravnoLice;
import rs.ac.uns.ftn.model.dto.tipovi.TPromene;
import rs.ac.uns.ftn.model.dto.zahtev_za_izvod.ZahtevZaIzvod;
import rs.ac.uns.ftn.model.environment.EnvironmentProperties;
import rs.ac.uns.ftn.repository.AnalitikaIzvodaRepository;
import rs.ac.uns.ftn.repository.DnevnoStanjeRacunaRepository;
import rs.ac.uns.ftn.repository.RacunRepository;
import rs.ac.uns.ftn.service.PresekService;
import rs.ac.uns.ftn.service.ZahtevService;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by zlatan on 26/06/2017.
 */
@Service
public class ZahtevServiceImpl implements ZahtevService {

    @Autowired
    private RacunRepository racunRepository;

    @Autowired
    private DnevnoStanjeRacunaRepository dnevnoStanjeRacunaRepository;

    @Autowired
    private AnalitikaIzvodaRepository analitikaIzvodaRepository;

    @Autowired
    private EnvironmentProperties environmentProperties;

    @Autowired
    private PresekService presekService;

    @Override
    public void process(ZahtevZaIzvod zahtevZaIzvod) {
        int trazeniPresek = zahtevZaIzvod.getRedniBrojPreseka().intValue();
        int ukupanBrojPreseka = 0;
        String brojRacuna = zahtevZaIzvod.getBrojRacuna();
        Date datumZahteva = zahtevZaIzvod.getDatum().toGregorianCalendar().getTime();
        if(proveriRacun(brojRacuna)){
            Racun racun = racunRepository.findByBrojRacuna(brojRacuna).get();
            Optional<DnevnoStanjeRacuna> dnevnoStanjeRacuna = dnevnoStanjeRacunaRepository.findByRacunAndDatum(racun, datumZahteva);
            if(dnevnoStanjeRacuna.isPresent()){
                DnevnoStanjeRacuna dnevnoStanjeRacunaReal = dnevnoStanjeRacuna.get();
                List<AnalitikaIzvoda> analitike = analitikaIzvodaRepository.findByDnevnoStanjeRacuna(dnevnoStanjeRacunaReal);
                int brojAnalitika = analitike.size();
                if(brojAnalitika % 3 != 0){
                    ukupanBrojPreseka= Math.round(brojAnalitika / 3 + 1);
                }else {
                    ukupanBrojPreseka = brojAnalitika / 3;
                }
                if(ukupanBrojPreseka < trazeniPresek){
                    throw new ServiceFaultException("Nije pronadjen.", new ServiceFault("404", "Trazeni redni broj presek ne postoji!"));
                }
                if(analitike.size() == 0){
                    throw new ServiceFaultException("Nije pronadjen.", new ServiceFault("404", "Nije pronadjena nijedna transakcija za prosledjeni datum!"));
                }else {
                   if(brojAnalitika > 3){
                       int pocetak = (trazeniPresek -1) * 3;
                       int kraj = pocetak + 3;
                       if(kraj > (analitike.size() - 1)){
                           kraj = analitike.size();
                       }
                       List<AnalitikaIzvoda> helperList = analitike.subList(pocetak, kraj);
                       Presek presek = napraviPresek(dnevnoStanjeRacunaReal, helperList, ukupanBrojPreseka);
                       presekService.send(presek);
                   }else {
                      Presek presek = napraviPresek(dnevnoStanjeRacunaReal, analitike, ukupanBrojPreseka);
                      presekService.send(presek);
                   }
                }
            }else{
                throw new ServiceFaultException("Nije pronadjen.", new ServiceFault("404", "Nije pronadjeno dnevno stanje za racun u banci!"));
            }
        }else{
            throw new ServiceFaultException("Nije pronadjen.", new ServiceFault("404", "Racun zahteva nije pronadjen u banci!"));
        }
    }

    private Presek napraviPresek(DnevnoStanjeRacuna dnevnoStanjeRacuna, List<AnalitikaIzvoda> analitikaIzvodaList, int brojPreseka) {
        Presek presek = new Presek();
        ZaglavljePreseka zaglavljePreseka = new ZaglavljePreseka();
        List<StavkaPreseka> stavkePreseka = new ArrayList<>();

        zaglavljePreseka.setBrojPreseka(BigInteger.valueOf(brojPreseka));
        zaglavljePreseka.setBrojRacuna(dnevnoStanjeRacuna.getRacun().getBrojRacuna());
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(dnevnoStanjeRacuna.getDatum());
        try {
            zaglavljePreseka.setDatumNaloga(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        zaglavljePreseka.setPrethodnoStanje(BigDecimal.valueOf(dnevnoStanjeRacuna.getPredhodnoStanje()));
        zaglavljePreseka.setNovoStanje(BigDecimal.valueOf(dnevnoStanjeRacuna.getNovoStanje()));

        TPromene promeneTeret = new TPromene();
        promeneTeret.setBrojPromena(BigInteger.valueOf(dnevnoStanjeRacuna.getBrojPromenaNaTeret()));
        promeneTeret.setUkupno(BigDecimal.valueOf(dnevnoStanjeRacuna.getUkupnoNaTeret()));

        TPromene promeneKorist = new TPromene();
        promeneKorist.setBrojPromena(BigInteger.valueOf(dnevnoStanjeRacuna.getBrojPromenaUKorist()));
        promeneKorist.setUkupno(BigDecimal.valueOf(dnevnoStanjeRacuna.getUkupnoUKorist()));

        zaglavljePreseka.setTeret(promeneTeret);
        zaglavljePreseka.setKorist(promeneKorist);

        for (AnalitikaIzvoda analitika : analitikaIzvodaList) {
            StavkaPreseka stavkaPreseka = new StavkaPreseka();

            TPravnoLice duznik = new TPravnoLice();
            duznik.setBrojRacuna(analitika.getRacunDuznika());
            duznik.setNaziv(analitika.getDuznik());
            stavkaPreseka.setPodaciODuzniku(duznik);

            TPravnoLice poverilac = new TPravnoLice();
            poverilac.setBrojRacuna(analitika.getRacunPoverioca());
            poverilac.setNaziv(analitika.getPoverilac());
            stavkaPreseka.setPodaciOPoveriocu(poverilac);

            StavkaPreseka.PodaciOUplati uplata = new StavkaPreseka.PodaciOUplati();
            uplata.setSvrhaPlacanja(analitika.getSvrhaPlacanja());
            uplata.setIznos(analitika.getIznos());
            uplata.setSmer((analitika.isPrimljeno()) ? "K" : "T");

            GregorianCalendar gregorNalog = new GregorianCalendar();
            GregorianCalendar gregorValuta = new GregorianCalendar();
            gregorNalog.setTime(analitika.getDatumNaloga());
            gregorValuta.setTime(analitika.getDatumValute());
            try {
                uplata.setDatumNaloga(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorNalog));
                uplata.setDatumValute(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorValuta));
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }

            TPodaciPlacanje zaduzenje = new TPodaciPlacanje();
            zaduzenje.setModel(BigInteger.valueOf(analitika.getModelZaduzenja()));
            zaduzenje.setPozivNaBroj(analitika.getPozivNaBrojZaduzenja());
            uplata.setPodaciOZaduzenju(zaduzenje);

            TPodaciPlacanje odobrenje = new TPodaciPlacanje();
            odobrenje.setModel(BigInteger.valueOf(analitika.getModelOdobrenja()));
            odobrenje.setPozivNaBroj(analitika.getPozivNaBrojOdobrenja());
            uplata.setPodaciOOdobrenju(odobrenje);

            stavkaPreseka.setPodaciOUplati(uplata);
            stavkePreseka.add(stavkaPreseka);
        }

        presek.setZaglavljePreseka(zaglavljePreseka);
        presek.setStavkaPreseka(stavkePreseka);

        return presek;
    }

    private boolean proveriRacun(String brojRacuna) {
        Optional<Racun> racun = racunRepository.findByBrojRacuna(brojRacuna);
        if(racun.isPresent()){
            String swiftCode = racun.get().getBanka().getSWIFTkod();
            if(swiftCode.equals(environmentProperties.getSwiftCode())){
                return true;
            }else {
                return false;
            }
        }else{
            return false;
        }
    }
}

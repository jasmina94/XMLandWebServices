package rs.ac.uns.ftn.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.exception.ServiceFaultException;
import rs.ac.uns.ftn.model.database.AnalitikaIzvoda;
import rs.ac.uns.ftn.model.database.Banka;
import rs.ac.uns.ftn.model.database.DnevnoStanjeRacuna;
import rs.ac.uns.ftn.model.database.Racun;
import rs.ac.uns.ftn.model.dto.error.ServiceFault;
import rs.ac.uns.ftn.model.dto.mt103.Mt103;
import rs.ac.uns.ftn.model.dto.nalog_za_prenos.NalogZaPrenos;
import rs.ac.uns.ftn.model.dto.tipovi.TPodaciBanka;
import rs.ac.uns.ftn.model.dto.tipovi.TPodaciPlacanje;
import rs.ac.uns.ftn.model.dto.tipovi.TPrenosUcesnik;
import rs.ac.uns.ftn.repository.AnalitikaIzvodaRepository;
import rs.ac.uns.ftn.repository.BankaRepository;
import rs.ac.uns.ftn.repository.DnevnoStanjeRacunaRepository;
import rs.ac.uns.ftn.repository.RacunRepository;
import rs.ac.uns.ftn.service.ClearingService;
import rs.ac.uns.ftn.service.PlacanjeService;
import rs.ac.uns.ftn.service.RTGSService;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by zlatan on 6/24/17.
 */
@Service
public class PlacanjeServiceImpl implements PlacanjeService {

    @Autowired
    private RacunRepository repozitorijumRacuna;

    @Autowired
    private AnalitikaIzvodaRepository repozitorijumAnalitika;

    @Autowired
    private DnevnoStanjeRacunaRepository repozitorijumDnevnoStanjeRacuna;

    @Autowired
    private BankaRepository repozitorijumBanka;

    @Autowired
    private RTGSService RTGSService;

    @Autowired
    private ClearingService ClearingService;


    @Override
    public void process(NalogZaPrenos nalogZaPrenos) {

        boolean duznikKodNas = proveriFirmu(nalogZaPrenos.getPodaciOPrenosu().getDuznikUPrenosu().getRacunUcesnika());
        boolean poverilacKodNas = proveriFirmu(nalogZaPrenos.getPodaciOPrenosu().getPoverilacUPrenosu().getRacunUcesnika());


        if(!duznikKodNas) {
            throw new ServiceFaultException("Nije pronadjen", new ServiceFault("404", "Racun duznika nije pronadjen!"));
        }else if(duznikKodNas && poverilacKodNas) {
            unutrasnjiPromet(nalogZaPrenos);
        } else if(duznikKodNas && !poverilacKodNas) {
            medjubankarskiPromet(nalogZaPrenos);
        }



    }

    @Override
    public void send(NalogZaPrenos nalogZaPrenos) {

    }

    private boolean proveriFirmu(String brojRacuna) {
        return repozitorijumRacuna.findByBrojRacuna(brojRacuna).isPresent();
    }

    private void unutrasnjiPromet(NalogZaPrenos nalog) {
        //uzmi duznika
        TPrenosUcesnik duznik = nalog.getPodaciOPrenosu().getDuznikUPrenosu();
        TPrenosUcesnik poverilac = nalog.getPodaciOPrenosu().getPoverilacUPrenosu();

        Racun racunDuznika = repozitorijumRacuna.findByBrojRacuna(duznik.getRacunUcesnika()).get();
        Racun racunPoverioca = repozitorijumRacuna.findByBrojRacuna(poverilac.getRacunUcesnika()).get();

        racunDuznika.setSaldo(racunDuznika.getSaldo() - nalog.getPodaciOPrenosu().getIznos().doubleValue());
        racunPoverioca.setSaldo(racunPoverioca.getSaldo() + nalog.getPodaciOPrenosu().getIznos().doubleValue());

        //sacuvaj racune

        repozitorijumRacuna.save(racunDuznika);
        repozitorijumRacuna.save(racunPoverioca);


        //napravi analitiku duznika

        napraviAnalitike(nalog, racunDuznika, racunPoverioca);

        //poverilaca uvecaj
    }

    private void medjubankarskiPromet(NalogZaPrenos nalog) {
        TPrenosUcesnik duznik = nalog.getPodaciOPrenosu().getDuznikUPrenosu();
        TPrenosUcesnik poverilac = nalog.getPodaciOPrenosu().getPoverilacUPrenosu();

        Racun racunDuznika = repozitorijumRacuna.findByBrojRacuna(duznik.getRacunUcesnika()).get();
        Racun racunPoverioca = repozitorijumRacuna.findByBrojRacuna(poverilac.getRacunUcesnika()).get();

        if(nalog.isHitno() || nalog.getPodaciOPrenosu().getIznos().doubleValue() >= 250000.00){
           Mt103 mt103 = createMt103(nalog, racunDuznika, racunPoverioca);
           RTGSService.processMT103(mt103);
        }else{
        //    Clearing();
        }
    }

    private Mt103 createMt103(NalogZaPrenos nalog, Racun racunDuznika, Racun racunPoverioca) {
        Mt103 mt103 = new Mt103();
        mt103.setIdPoruke(UUID.randomUUID().toString());
        Mt103.PodaciODuzniku podaciODuzniku = new Mt103.PodaciODuzniku();
        podaciODuzniku.setNaziv(nalog.getDuznik());
        podaciODuzniku.setBrojRacuna(nalog.getPodaciOPrenosu().getDuznikUPrenosu().getRacunUcesnika());

        Mt103.PodaciOPoveriocu podaciOPoveriocu = new Mt103.PodaciOPoveriocu();
        podaciOPoveriocu.setNaziv(nalog.getPoverilac());
        podaciOPoveriocu.setBrojRacuna(nalog.getPodaciOPrenosu().getPoverilacUPrenosu().getRacunUcesnika());

        Optional<Banka> bankaDuznika = repozitorijumBanka.findById(racunDuznika.getBanka().getId());
        Optional<Banka> bankaPoverioca = repozitorijumBanka.findById(racunPoverioca.getBanka().getId());

        if(!bankaDuznika.isPresent() || !bankaPoverioca.isPresent())
            throw new ServiceFaultException("Nije pronadjen", new ServiceFault("404", "Banka ucesnika prenosa nije pronadjena!"));


        TPodaciBanka podaciBankaDuznika = new TPodaciBanka();
        podaciBankaDuznika.setObracunskiRacun(bankaDuznika.get().getObracunskiRacun());
        podaciBankaDuznika.setSwiftKod(bankaDuznika.get().getSWIFTkod());
        podaciODuzniku.setPodaciOBanci(podaciBankaDuznika);

        mt103.setPodaciODuzniku(podaciODuzniku);

        TPodaciBanka podaciBankaPoverioca = new TPodaciBanka();
        podaciBankaPoverioca.setObracunskiRacun(bankaDuznika.get().getObracunskiRacun());
        podaciBankaPoverioca.setSwiftKod(bankaDuznika.get().getSWIFTkod());
        podaciOPoveriocu.setPodaciOBanci(podaciBankaPoverioca);

        mt103.setPodaciOPoveriocu(podaciOPoveriocu);

        Mt103.PodaciOUplati podaciOUplati = new Mt103.PodaciOUplati();
        podaciOUplati.setDatumNaloga(nalog.getDatumNaloga());
        podaciOUplati.setDatumValute(nalog.getDatumValute());
        Mt103.PodaciOUplati.Iznos iznos = new Mt103.PodaciOUplati.Iznos();
        iznos.setValue(nalog.getPodaciOPrenosu().getIznos());
        iznos.setValuta(nalog.getPodaciOPrenosu().getOznakaValute().value());
        podaciOUplati.setIznos(iznos);
        TPodaciPlacanje podaciZaduzenje = new TPodaciPlacanje();
        TPodaciPlacanje podaciOdobrenje = new TPodaciPlacanje();

        podaciZaduzenje.setModel(BigInteger.valueOf(nalog.getPodaciOPrenosu().getDuznikUPrenosu().getModelPrenosa()));
        podaciZaduzenje.setPozivNaBroj(nalog.getPodaciOPrenosu().getDuznikUPrenosu().getPozivNaBroj());

        podaciOdobrenje.setModel(BigInteger.valueOf(nalog.getPodaciOPrenosu().getPoverilacUPrenosu().getModelPrenosa()));
        podaciOdobrenje.setPozivNaBroj(nalog.getPodaciOPrenosu().getPoverilacUPrenosu().getPozivNaBroj());

        podaciOUplati.setPodaciOOdobrenju(podaciOdobrenje);
        podaciOUplati.setPodaciOZaduzenju(podaciZaduzenje);
        podaciOUplati.setSvrhaPlacanja(nalog.getSvrhaPlacanja());


        mt103.setPodaciOUplati(podaciOUplati);

        return mt103;
    }

    private void napraviAnalitike(NalogZaPrenos nalog, Racun racunDuznika, Racun racunPoverioca){
        AnalitikaIzvoda analitikaDuznika = new AnalitikaIzvoda();
        AnalitikaIzvoda analitikaPoverioca = new AnalitikaIzvoda();

        analitikaDuznika.setDatumNaloga(nalog.getDatumNaloga().toGregorianCalendar().getTime());
        analitikaDuznika.setPrimljeno(false);
        analitikaDuznika.setDuznik(nalog.getDuznik());
        analitikaDuznika.setPoverilac(nalog.getPoverilac());
        analitikaDuznika.setDatumValute(nalog.getDatumValute().toGregorianCalendar().getTime());
        analitikaDuznika.setRacunDuznika(nalog.getPodaciOPrenosu().getDuznikUPrenosu().getRacunUcesnika());
        analitikaDuznika.setModelZaduzenja(nalog.getPodaciOPrenosu().getDuznikUPrenosu().getModelPrenosa());
        analitikaDuznika.setPozivNaBrojZaduzenja(nalog.getPodaciOPrenosu().getDuznikUPrenosu().getPozivNaBroj());
        analitikaDuznika.setRacunPoverioca(nalog.getPodaciOPrenosu().getPoverilacUPrenosu().getRacunUcesnika());
        analitikaDuznika.setModelOdobrenja(nalog.getPodaciOPrenosu().getPoverilacUPrenosu().getModelPrenosa());
        analitikaDuznika.setPozivNaBrojZaduzenja(nalog.getPodaciOPrenosu().getDuznikUPrenosu().getPozivNaBroj());
        analitikaDuznika.setPozivNaBrojOdobrenja(nalog.getPodaciOPrenosu().getPoverilacUPrenosu().getPozivNaBroj());
        analitikaDuznika.setIznos(nalog.getPodaciOPrenosu().getIznos());
        analitikaDuznika.setSifraValute(nalog.getPodaciOPrenosu().getOznakaValute().value());
        analitikaDuznika.setSvrhaPlacanja(nalog.getSvrhaPlacanja());
        //analitikaDuznika.setDnevnoStanjeRacuna(repozitorijumDnevnoStanjeRacuna.findByRacun(racunDuznika));

        repozitorijumAnalitika.save(analitikaDuznika);

        analitikaPoverioca.setDatumNaloga(nalog.getDatumNaloga().toGregorianCalendar().getTime());
        analitikaPoverioca.setPrimljeno(true);
        analitikaPoverioca.setDuznik(nalog.getDuznik());
        analitikaPoverioca.setPoverilac(nalog.getPoverilac());
        analitikaPoverioca.setDatumValute(nalog.getDatumValute().toGregorianCalendar().getTime());
        analitikaPoverioca.setRacunDuznika(nalog.getPodaciOPrenosu().getDuznikUPrenosu().getRacunUcesnika());
        analitikaPoverioca.setModelZaduzenja(nalog.getPodaciOPrenosu().getDuznikUPrenosu().getModelPrenosa());
        analitikaPoverioca.setPozivNaBrojZaduzenja(nalog.getPodaciOPrenosu().getDuznikUPrenosu().getPozivNaBroj());
        analitikaPoverioca.setRacunPoverioca(nalog.getPodaciOPrenosu().getPoverilacUPrenosu().getRacunUcesnika());
        analitikaPoverioca.setModelOdobrenja(nalog.getPodaciOPrenosu().getPoverilacUPrenosu().getModelPrenosa());
        analitikaPoverioca.setPozivNaBrojOdobrenja(nalog.getPodaciOPrenosu().getPoverilacUPrenosu().getPozivNaBroj());
        analitikaPoverioca.setIznos(nalog.getPodaciOPrenosu().getIznos());
        analitikaPoverioca.setSifraValute(nalog.getPodaciOPrenosu().getOznakaValute().value());
        analitikaPoverioca.setSvrhaPlacanja(nalog.getSvrhaPlacanja());
        //analitikaPoverioca.setDnevnoStanjeRacuna(repozitorijumDnevnoStanjeRacuna.findByRacun(racunPoverioca));

        repozitorijumAnalitika.save(analitikaPoverioca);

        evidentirajDnevnoStanje(analitikaDuznika, nalog, racunDuznika, true);
        evidentirajDnevnoStanje(analitikaPoverioca, nalog, racunPoverioca, false);

    }

    private void evidentirajDnevnoStanje(AnalitikaIzvoda analitika, NalogZaPrenos nalog, Racun racun, boolean isDuzan){
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
                    dsr.setNovoStanje(dsr.getNovoStanje() - nalog.getPodaciOPrenosu().getIznos().doubleValue());
                }else{
                    dsr.setNovoStanje(dsr.getNovoStanje() + nalog.getPodaciOPrenosu().getIznos().doubleValue());
                }

                dsr.getAnalitikeIzvoda().add(analitika);
                analitika.setDnevnoStanjeRacuna(dsr);
                dsr = repozitorijumDnevnoStanjeRacuna.save(dsr);
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
                dnevnoStanjeRacuna.setPredhodnoStanje(racun.getSaldo() + nalog.getPodaciOPrenosu().getIznos().doubleValue());
                dnevnoStanjeRacuna.setNovoStanje(racun.getSaldo());
                dnevnoStanjeRacuna.setPrometNaTeret(1);
            }else{
                dnevnoStanjeRacuna.setPredhodnoStanje(racun.getSaldo() - nalog.getPodaciOPrenosu().getIznos().doubleValue());
                dnevnoStanjeRacuna.setNovoStanje(racun.getSaldo());
                dnevnoStanjeRacuna.setPrometuKorist(1);
            }

            dnevnoStanjeRacuna = repozitorijumDnevnoStanjeRacuna.save(dnevnoStanjeRacuna);
            racun.getDnevnoStanjeRacuna().add(dnevnoStanjeRacuna);
            analitika.setDnevnoStanjeRacuna(dnevnoStanjeRacuna);
        }

        repozitorijumRacuna.save(racun);
        repozitorijumAnalitika.save(analitika);
    }

}

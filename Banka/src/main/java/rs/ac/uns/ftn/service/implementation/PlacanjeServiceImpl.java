package rs.ac.uns.ftn.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.exception.ServiceFaultException;
import rs.ac.uns.ftn.model.database.AnalitikaIzvoda;
import rs.ac.uns.ftn.model.database.DnevnoStanjeRacuna;
import rs.ac.uns.ftn.model.database.Racun;
import rs.ac.uns.ftn.model.dto.error.ServiceFault;
import rs.ac.uns.ftn.model.dto.nalog_za_prenos.NalogZaPrenos;
import rs.ac.uns.ftn.model.dto.tipovi.TPrenosUcesnik;
import rs.ac.uns.ftn.repository.AnalitikaIzvodaRepository;
import rs.ac.uns.ftn.repository.DnevnoStanjeRacunaRepository;
import rs.ac.uns.ftn.repository.RacunRepository;
import rs.ac.uns.ftn.service.PlacanjeService;

import java.util.Calendar;
import java.util.Date;

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

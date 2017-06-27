package rs.ac.uns.ftn.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.exception.ServiceFaultException;
import rs.ac.uns.ftn.model.database.*;
import rs.ac.uns.ftn.model.dto.error.ServiceFault;
import rs.ac.uns.ftn.model.dto.mt102.Mt102;
import rs.ac.uns.ftn.model.dto.mt103.Mt103;
import rs.ac.uns.ftn.model.dto.nalog_za_prenos.NalogZaPrenos;
import rs.ac.uns.ftn.model.dto.tipovi.*;
import rs.ac.uns.ftn.model.environment.EnvironmentProperties;
import rs.ac.uns.ftn.repository.*;
import rs.ac.uns.ftn.service.ClearingService;
import rs.ac.uns.ftn.service.PlacanjeService;
import rs.ac.uns.ftn.service.RTGSService;

import java.math.BigInteger;
import java.util.*;

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
    private ClearingService clearingService;

    @Autowired
    private EnvironmentProperties environmentProperties;

    @Autowired
    private Mt102Repository mt102Repository;

    @Autowired
    private PojedinacniNalogZaPlacanjeRepository pojedinacniNalogZaPlacanjeRepository;

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

    private boolean proveriFirmu(String brojRacuna) {
        Optional<Racun> racun = repozitorijumRacuna.findByBrojRacuna(brojRacuna);
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

    private void unutrasnjiPromet(NalogZaPrenos nalog) {
        TPrenosUcesnik duznik = nalog.getPodaciOPrenosu().getDuznikUPrenosu();
        TPrenosUcesnik poverilac = nalog.getPodaciOPrenosu().getPoverilacUPrenosu();

        Racun racunDuznika = repozitorijumRacuna.findByBrojRacuna(duznik.getRacunUcesnika()).get();
        Racun racunPoverioca = repozitorijumRacuna.findByBrojRacuna(poverilac.getRacunUcesnika()).get();
        racunDuznika.setSaldo(racunDuznika.getSaldo() - nalog.getPodaciOPrenosu().getIznos().doubleValue());
        racunPoverioca.setSaldo(racunPoverioca.getSaldo() + nalog.getPodaciOPrenosu().getIznos().doubleValue());
        repozitorijumRacuna.save(racunDuznika);
        repozitorijumRacuna.save(racunPoverioca);
        napraviAnalitike(nalog, racunDuznika, racunPoverioca);
    }

    private void medjubankarskiPromet(NalogZaPrenos nalog) {
        TPrenosUcesnik duznik = nalog.getPodaciOPrenosu().getDuznikUPrenosu();
        TPrenosUcesnik poverilac = nalog.getPodaciOPrenosu().getPoverilacUPrenosu();

        Racun racunDuznika = repozitorijumRacuna.findByBrojRacuna(duznik.getRacunUcesnika()).get();
        Racun racunPoverioca = repozitorijumRacuna.findByBrojRacuna(poverilac.getRacunUcesnika()).get();

        if(nalog.isHitno() || nalog.getPodaciOPrenosu().getIznos().doubleValue() >= 250000.00){
            // RTGS
            racunDuznika.setRezervisanaSredstva(nalog.getPodaciOPrenosu().getIznos().doubleValue());
            repozitorijumRacuna.save(racunDuznika);
            Mt103 mt103 = createMt103(nalog, racunDuznika, racunPoverioca);
            RTGSService.processMT103(mt103);
        }else{
            // Clearing i settlement
            String swiftBankeDuznika = environmentProperties.getSwiftCode();
            String swiftBankePoverioca = "";
            Optional<Banka> bankaPoverioca = repozitorijumBanka.findById(racunPoverioca.getBanka().getId());
            Banka bankaPoveriocaReal = null;
            if(!bankaPoverioca.isPresent()){
                throw new ServiceFaultException("Nije pronadjen", new ServiceFault("404", "Banka poverioca nije pronadjena!"));
            }else{
                bankaPoveriocaReal = bankaPoverioca.get();
                swiftBankePoverioca = bankaPoveriocaReal.getSWIFTkod();
            }
            //Rezervisi sredstva
            racunDuznika.setRezervisanaSredstva(racunDuznika.getRezervisanaSredstva() + nalog.getPodaciOPrenosu().getIznos().doubleValue());
            repozitorijumRacuna.save(racunDuznika);

            List<Mt102Model> mt102Model = mt102Repository.findBySwiftBankeDuznikaAndSwiftBankePoveriocaAndPoslato(swiftBankeDuznika, swiftBankePoverioca, false);
            if(mt102Model.isEmpty()){
                kreirajNoviMt102Model(nalog, racunDuznika, racunPoverioca);
            }else{
                PojedinacniNalogZaPlacanje pojedinacniNalogZaPlacanje = kreiranjeNalogaZaPlacanje(nalog);
                pojedinacniNalogZaPlacanje.setMt102Model(mt102Model.get(0));
                mt102Model.get(0).getListaNalogaZaPlacanje().add(pojedinacniNalogZaPlacanje);
                mt102Model.get(0).setUkupanIznos(mt102Model.get(0).getUkupanIznos() + pojedinacniNalogZaPlacanje.getIznos());
                pojedinacniNalogZaPlacanjeRepository.save(pojedinacniNalogZaPlacanje);
                mt102Repository.save(mt102Model.get(0));
                if(mt102Model.get(0).getListaNalogaZaPlacanje().size() >= 2){
                    Mt102 mt102 = clearingService.createMT102(mt102Model.get(0));
                    mt102Model.get(0).setPoslato(true);
                    mt102Repository.save(mt102Model.get(0));
                    clearingService.sendMT102(mt102);
                }
            }
       }
    }

    private void kreirajNoviMt102Model(NalogZaPrenos nalog, Racun racunDuznika, Racun racunPoverioca) {
        Mt102Model mt102Model = new Mt102Model();
        Optional<Banka> bankaDuznika = repozitorijumBanka.findById(racunDuznika.getBanka().getId());
        Optional<Banka> bankaPoverioca = repozitorijumBanka.findById(racunPoverioca.getBanka().getId());
        if(!bankaPoverioca.isPresent() || !bankaDuznika.isPresent()){
            throw new ServiceFaultException("Nije pronadjen.", new ServiceFault("404", "Banka poverioca ili banka duznika nisu pronadjene!"));
        }

        mt102Model.setIdPoruke(UUID.randomUUID().toString());
        mt102Model.setSwiftBankeDuznika(environmentProperties.getSwiftCode());
        mt102Model.setSwiftBankePoverioca(bankaPoverioca.get().getSWIFTkod());
        mt102Model.setRacunBankeDuznika(bankaDuznika.get().getObracunskiRacun());
        mt102Model.setRacunBankePoverioca(bankaPoverioca.get().getObracunskiRacun());
        mt102Model.setUkupanIznos(mt102Model.getUkupanIznos() + nalog.getPodaciOPrenosu().getIznos().doubleValue());
        mt102Model.setSifraValute(nalog.getPodaciOPrenosu().getOznakaValute().value());
        mt102Model.setDatumValute(nalog.getDatumValute().toGregorianCalendar().getTime());
        mt102Model.setDatumNaloga(nalog.getDatumNaloga().toGregorianCalendar().getTime());
        mt102Model.setPoslato(false);

        List<PojedinacniNalogZaPlacanje> naloziZaPlacanje = new ArrayList<>();
        mt102Model.setListaNalogaZaPlacanje(naloziZaPlacanje);

        PojedinacniNalogZaPlacanje pojedinacniNalogZaPlacanje = kreiranjeNalogaZaPlacanje(nalog);
        mt102Model.getListaNalogaZaPlacanje().add(pojedinacniNalogZaPlacanje);
        mt102Model = mt102Repository.save(mt102Model);
        pojedinacniNalogZaPlacanje.setMt102Model(mt102Model);
        pojedinacniNalogZaPlacanjeRepository.save(pojedinacniNalogZaPlacanje);
    }

    private PojedinacniNalogZaPlacanje kreiranjeNalogaZaPlacanje(NalogZaPrenos nalog) {
        PojedinacniNalogZaPlacanje pnzp = new PojedinacniNalogZaPlacanje();
        pnzp.setIdNaloga(nalog.getIdPoruke());
        pnzp.setDuznik(nalog.getDuznik());
        pnzp.setPoverilac(nalog.getPoverilac());
        pnzp.setSvrhaPlacanja(nalog.getSvrhaPlacanja());
        pnzp.setDatumNaloga(nalog.getDatumNaloga().toGregorianCalendar().getTime());
        pnzp.setRacunDuznika(nalog.getPodaciOPrenosu().getDuznikUPrenosu().getRacunUcesnika());
        pnzp.setRacunPoverioca(nalog.getPodaciOPrenosu().getPoverilacUPrenosu().getRacunUcesnika());
        pnzp.setModelZaduzenja(((int) nalog.getPodaciOPrenosu().getDuznikUPrenosu().getModelPrenosa()));
        pnzp.setModelOdobrenja((int) nalog.getPodaciOPrenosu().getPoverilacUPrenosu().getModelPrenosa());
        pnzp.setPozivNaBrojZaduzenja(nalog.getPodaciOPrenosu().getDuznikUPrenosu().getPozivNaBroj());
        pnzp.setPozivNaBrojOdobrenja(nalog.getPodaciOPrenosu().getPoverilacUPrenosu().getPozivNaBroj());
        pnzp.setIznos(nalog.getPodaciOPrenosu().getIznos().doubleValue());
        pnzp.setSifraValute(nalog.getPodaciOPrenosu().getOznakaValute().value());
        return pnzp;
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
        podaciBankaPoverioca.setObracunskiRacun(bankaPoverioca.get().getObracunskiRacun());
        podaciBankaPoverioca.setSwiftKod(bankaPoverioca.get().getSWIFTkod());
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

        repozitorijumAnalitika.save(analitikaPoverioca);

        evidentirajDnevnoStanje(analitikaDuznika, nalog, racunDuznika, true);
        evidentirajDnevnoStanje(analitikaPoverioca, nalog, racunPoverioca, false);
    }

    private void evidentirajDnevnoStanje(AnalitikaIzvoda analitika, NalogZaPrenos nalog, Racun racun, boolean isDuzan){
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
                dsr.setPredhodnoStanje(dsr.getNovoStanje());
                if(isDuzan) {
                    dsr.setPredhodnoStanje(dsr.getNovoStanje());
                    dsr.setNovoStanje(dsr.getNovoStanje() - nalog.getPodaciOPrenosu().getIznos().doubleValue());
                }else{
                    dsr.setPredhodnoStanje(dsr.getNovoStanje());
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
                dnevnoStanjeRacuna.setBrojPromenaNaTeret(1);
            }else{
                dnevnoStanjeRacuna.setPredhodnoStanje(racun.getSaldo() - nalog.getPodaciOPrenosu().getIznos().doubleValue());
                dnevnoStanjeRacuna.setNovoStanje(racun.getSaldo());
                dnevnoStanjeRacuna.setBrojPromenaUKorist(1);
            }

            dnevnoStanjeRacuna = repozitorijumDnevnoStanjeRacuna.save(dnevnoStanjeRacuna);
            racun.getDnevnoStanjeRacuna().add(dnevnoStanjeRacuna);
            analitika.setDnevnoStanjeRacuna(dnevnoStanjeRacuna);
        }

        repozitorijumRacuna.save(racun);
        repozitorijumAnalitika.save(analitika);
    }
}

package rs.ac.uns.ftn.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import rs.ac.uns.ftn.exception.ServiceFaultException;
import rs.ac.uns.ftn.model.database.AnalitikaIzvoda;
import rs.ac.uns.ftn.model.database.DnevnoStanjeRacuna;
import rs.ac.uns.ftn.model.database.Mt103Model;
import rs.ac.uns.ftn.model.database.Racun;
import rs.ac.uns.ftn.model.dto.error.ServiceFault;
import rs.ac.uns.ftn.model.dto.mt102.GetMt102Response;
import rs.ac.uns.ftn.model.dto.mt103.GetMt103Request;
import rs.ac.uns.ftn.model.dto.mt103.GetMt103Response;
import rs.ac.uns.ftn.model.dto.mt103.Mt103;
import rs.ac.uns.ftn.model.dto.mt900.Mt900;
import rs.ac.uns.ftn.model.dto.mt910.Mt910;
import rs.ac.uns.ftn.model.environment.EnvironmentProperties;
import rs.ac.uns.ftn.repository.*;
import rs.ac.uns.ftn.service.RTGSService;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zlatan on 6/25/17.
 */
@Service
public class RTGSServiceImpl extends WebServiceGatewaySupport implements RTGSService {

    @Autowired
    private Mt103Repository mt103Repository;

    @Autowired
    private BankaRepository bankaRepository;

    @Autowired
    private EnvironmentProperties environmentProperties;

    @Autowired
    private AnalitikaIzvodaRepository analitikaIzvodaRepository;

    @Autowired
    private RacunRepository racunRepository;

    @Autowired
    private DnevnoStanjeRacunaRepository dnevnoStanjeRacunaRepository;

    @Override
    public void processMT103(Mt103 mt103) {
        Mt103Model mt103Model = new Mt103Model();
        mt103Model.setIdPoruke(mt103.getIdPoruke());
        mt103Model.setDatumNaloga(mt103.getPodaciOUplati().getDatumNaloga().toGregorianCalendar().getTime());
        mt103Model.setDatumValute(mt103.getPodaciOUplati().getDatumValute().toGregorianCalendar().getTime());
        mt103Model.setDuznik(mt103.getPodaciODuzniku().getNaziv());
        mt103Model.setPoverilac(mt103.getPodaciOPoveriocu().getNaziv());
        mt103Model.setSWIFTBankeDuznika(mt103.getPodaciODuzniku().getPodaciOBanci().getSwiftKod());
        mt103Model.setSWIFTBankePoverioca(mt103.getPodaciOPoveriocu().getPodaciOBanci().getSwiftKod());
        mt103Model.setRacunBankeDuznika(mt103.getPodaciODuzniku().getPodaciOBanci().getObracunskiRacun());
        mt103Model.setRacunBankePoverioca(mt103.getPodaciOPoveriocu().getPodaciOBanci().getObracunskiRacun());
        mt103Model.setSvrhaPlacanja(mt103.getPodaciOUplati().getSvrhaPlacanja());
        mt103Model.setRacunDuznika(mt103.getPodaciODuzniku().getBrojRacuna());
        mt103Model.setRacunPoverioca(mt103.getPodaciOPoveriocu().getBrojRacuna());
        mt103Model.setModelZaduzenja(mt103.getPodaciOUplati().getPodaciOZaduzenju().getModel());
        mt103Model.setModelOdobrenja(mt103.getPodaciOUplati().getPodaciOOdobrenju().getModel());
        mt103Model.setPozivNaBrojZaduzenja(mt103.getPodaciOUplati().getPodaciOZaduzenju().getPozivNaBroj());
        mt103Model.setPozivNaBrojOdobrenja(mt103.getPodaciOUplati().getPodaciOOdobrenju().getPozivNaBroj());
        mt103Model.setSifraValute(mt103.getPodaciOUplati().getIznos().getValuta());
        mt103Model.setIznos(mt103.getPodaciOUplati().getIznos().getValue().doubleValue());

        mt103Repository.save(mt103Model);

        sendMT103(mt103);
    }

    @Override
    public void sendMT103(Mt103 mt103) {

        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(GetMt103Request.class, GetMt103Response.class);
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);

        final GetMt103Request getMt103Request = new GetMt103Request();
        getMt103Request.setMt103(mt103);

        try{
            final GetMt103Response response = (GetMt103Response) getWebServiceTemplate()
                    .marshalSendAndReceive(environmentProperties.getNbsUrl(), getMt103Request);
        }catch (RuntimeException e) {
            throw new ServiceFaultException("Pogresan odgovor.", new ServiceFault("500", "Nemoguce slanje Mt103 modela narodnoj banci!"));
        }
    }

    @Override
    public String processMT900(Mt900 mt900) {
        Optional<Mt103Model> mt103Model = mt103Repository.findByIdPoruke(mt900.getIdPoruke());

        if(!mt103Model.isPresent()){
            throw new ServiceFaultException("Nije pronadjen.", new ServiceFault("404", "Nije pronadjen Mt103 model u banci duznika!"));
        }else{
            Optional <Racun> racunDuznika = racunRepository.findByBrojRacuna(mt103Model.get().getRacunDuznika());
            if(!racunDuznika.isPresent()){
                throw new ServiceFaultException("Nije pronadjen.", new ServiceFault("404", "Nije pronadjen racun duznika!"));
            }else{
                Racun racunDuznikaReal = racunDuznika.get();
                racunDuznikaReal.setSaldo(racunDuznikaReal.getSaldo() - racunDuznika.get().getRezervisanaSredstva());
                racunDuznika.get().setRezervisanaSredstva(0.0);
                racunRepository.save(racunDuznikaReal);
                napraviAnalitiku(mt103Model.get(), racunDuznikaReal, true);
            }
        }
        return "ok";
    }

    @Override
    public String processMT910(Mt910 mt910) {
        Optional<Mt103Model> mt103Model = mt103Repository.findByIdPoruke(mt910.getIdPoruke());
        if(!mt103Model.isPresent()){
            return "Nisam pronasao model mt103Model.";
        }else{
            Optional <Racun> racunPoverioca = racunRepository.findByBrojRacuna(mt103Model.get().getRacunPoverioca());
            if(!racunPoverioca.isPresent()){
                return "Nema racuna poverioca";
            }else{
                Racun RacunPoveriocaReal = racunPoverioca.get();
                RacunPoveriocaReal.setSaldo(RacunPoveriocaReal.getSaldo() + mt103Model.get().getIznos());
                racunRepository.save(RacunPoveriocaReal);
                napraviAnalitiku(mt103Model.get(), RacunPoveriocaReal, false);
            }
        }
        return "ok";
    }

    @Override
    public void save(Mt103 mt103) {
        Mt103Model mt103Model = new Mt103Model();
        mt103Model.setIdPoruke(mt103.getIdPoruke());
        mt103Model.setDatumNaloga(mt103.getPodaciOUplati().getDatumNaloga().toGregorianCalendar().getTime());
        mt103Model.setDatumValute(mt103.getPodaciOUplati().getDatumValute().toGregorianCalendar().getTime());
        mt103Model.setDuznik(mt103.getPodaciODuzniku().getNaziv());
        mt103Model.setPoverilac(mt103.getPodaciOPoveriocu().getNaziv());
        mt103Model.setSWIFTBankeDuznika(mt103.getPodaciODuzniku().getPodaciOBanci().getSwiftKod());
        mt103Model.setSWIFTBankePoverioca(mt103.getPodaciOPoveriocu().getPodaciOBanci().getSwiftKod());
        mt103Model.setRacunBankeDuznika(mt103.getPodaciODuzniku().getPodaciOBanci().getObracunskiRacun());
        mt103Model.setRacunBankePoverioca(mt103.getPodaciOPoveriocu().getPodaciOBanci().getObracunskiRacun());
        mt103Model.setSvrhaPlacanja(mt103.getPodaciOUplati().getSvrhaPlacanja());
        mt103Model.setRacunDuznika(mt103.getPodaciODuzniku().getBrojRacuna());
        mt103Model.setRacunPoverioca(mt103.getPodaciOPoveriocu().getBrojRacuna());
        mt103Model.setModelZaduzenja(mt103.getPodaciOUplati().getPodaciOZaduzenju().getModel());
        mt103Model.setModelOdobrenja(mt103.getPodaciOUplati().getPodaciOOdobrenju().getModel());
        mt103Model.setPozivNaBrojZaduzenja(mt103.getPodaciOUplati().getPodaciOZaduzenju().getPozivNaBroj());
        mt103Model.setPozivNaBrojOdobrenja(mt103.getPodaciOUplati().getPodaciOOdobrenju().getPozivNaBroj());
        mt103Model.setSifraValute(mt103.getPodaciOUplati().getIznos().getValuta());
        mt103Model.setIznos(mt103.getPodaciOUplati().getIznos().getValue().doubleValue());
        mt103Repository.save(mt103Model);
    }

    private void napraviAnalitiku(Mt103Model mt103Model, Racun racunDuznik, boolean duznik){
        AnalitikaIzvoda analitika = new AnalitikaIzvoda();
        analitika.setDatumNaloga(mt103Model.getDatumNaloga());
        analitika.setPrimljeno(!duznik);
        analitika.setDuznik(mt103Model.getDuznik());
        analitika.setPoverilac(mt103Model.getPoverilac());
        analitika.setDatumValute(mt103Model.getDatumValute());
        analitika.setRacunDuznika(mt103Model.getRacunDuznika());
        analitika.setModelZaduzenja(mt103Model.getModelZaduzenja().longValue());
        analitika.setPozivNaBrojZaduzenja(mt103Model.getPozivNaBrojZaduzenja());
        analitika.setRacunPoverioca(mt103Model.getRacunPoverioca());
        analitika.setModelOdobrenja(mt103Model.getModelOdobrenja().longValue());
        analitika.setPozivNaBrojOdobrenja(mt103Model.getPozivNaBrojOdobrenja());
        analitika.setIznos(BigDecimal.valueOf(mt103Model.getIznos()));
        analitika.setSifraValute(mt103Model.getSifraValute());
        analitika.setSvrhaPlacanja(mt103Model.getSvrhaPlacanja());
        evidentirajDnevnoStanje(analitika, mt103Model, racunDuznik, duznik);
    }

    private void evidentirajDnevnoStanje(AnalitikaIzvoda analitika, Mt103Model mt103Model, Racun racun, boolean isDuzan){
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
                    dsr.setUkupnoNaTeret(dsr.getUkupnoNaTeret() + mt103Model.getIznos());
                    dsr.setNovoStanje(dsr.getNovoStanje() - mt103Model.getIznos());
                }else{
                    dsr.setUkupnoUKorist(dsr.getUkupnoUKorist() + mt103Model.getIznos());
                    dsr.setNovoStanje(dsr.getNovoStanje() + mt103Model.getIznos());
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
                dnevnoStanjeRacuna.setUkupnoNaTeret(dnevnoStanjeRacuna.getUkupnoNaTeret() + mt103Model.getIznos());
                dnevnoStanjeRacuna.setPredhodnoStanje(racun.getSaldo() + mt103Model.getIznos());
                dnevnoStanjeRacuna.setNovoStanje(racun.getSaldo());
                dnevnoStanjeRacuna.setBrojPromenaNaTeret(1);
            }else{
                dnevnoStanjeRacuna.setUkupnoUKorist(dnevnoStanjeRacuna.getUkupnoUKorist() + mt103Model.getIznos());
                dnevnoStanjeRacuna.setPredhodnoStanje(racun.getSaldo() - mt103Model.getIznos());
                dnevnoStanjeRacuna.setNovoStanje(racun.getSaldo());
                dnevnoStanjeRacuna.setBrojPromenaUKorist(1);
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

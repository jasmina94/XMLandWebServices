package rs.ac.uns.ftn.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import rs.ac.uns.ftn.model.database.AnalitikaIzvoda;
import rs.ac.uns.ftn.model.database.Mt103Model;
import rs.ac.uns.ftn.model.database.Racun;
import rs.ac.uns.ftn.model.dto.mt103.GetMt103Request;
import rs.ac.uns.ftn.model.dto.mt103.GetMt103Response;
import rs.ac.uns.ftn.model.dto.mt103.Mt103;
import rs.ac.uns.ftn.model.dto.mt900.Mt900;
import rs.ac.uns.ftn.model.dto.mt910.Mt910;
import rs.ac.uns.ftn.model.dto.nalog_za_prenos.NalogZaPrenos;
import rs.ac.uns.ftn.model.environment.EnvironmentProperties;
import rs.ac.uns.ftn.repository.AnalitikaIzvodaRepository;
import rs.ac.uns.ftn.repository.BankaRepository;
import rs.ac.uns.ftn.repository.Mt103Repository;
import rs.ac.uns.ftn.repository.RacunRepository;
import rs.ac.uns.ftn.service.RTGSService;

import java.util.Optional;

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
    private EnvironmentProperties environment;

    @Autowired
    private AnalitikaIzvodaRepository analitikaIzvodaRepository;

    @Autowired
    private RacunRepository racunRepository;

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

        final GetMt103Response response = (GetMt103Response) getWebServiceTemplate().marshalSendAndReceive(environment.getNbsUrl(), getMt103Request);
    }

    @Override
    public String processMT900(Mt900 mt900) {
        Optional<Mt103Model> mt103Model = mt103Repository.findByIdPoruke(mt900.getIdPoruke());

        if(!mt103Model.isPresent()){
            return "Nisam pronasao model mt103Model.";
        }else{
            Optional <Racun> racunDuznika = racunRepository.findByBrojRacuna(mt103Model.get().getRacunDuznika());
            if(!racunDuznika.isPresent()){
                return "Nema racuna duznika";
            }else{
                Racun racunDuznikaReal = racunDuznika.get();
                racunDuznikaReal.setSaldo(racunDuznikaReal.getSaldo() - mt103Model.get().getIznos());
                racunRepository.save(racunDuznikaReal);
                napraviAnalitiku(mt103Model.get(), racunDuznikaReal);
            }
        }

        return "ok";
    }

    @Override
    public void sendMT900(Mt900 mt900) {

    }

    @Override
    public void processMT910(Mt910 mt910) {

    }

    @Override
    public void sendMT910(Mt910 mt910) {

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

    private void napraviAnalitiku(Mt103Model mt103Model, Racun racunDuznik){
        AnalitikaIzvoda analitika = new AnalitikaIzvoda();

//        analitika.setDatumNaloga(nalog.getDatumNaloga().toGregorianCalendar().getTime());
//        analitika.setPrimljeno(false);
//        analitika.setDuznik(nalog.getDuznik());
//        analitika.setPoverilac(nalog.getPoverilac());
//        analitika.setDatumValute(nalog.getDatumValute().toGregorianCalendar().getTime());
//        analitika.setRacunDuznika(nalog.getPodaciOPrenosu().getDuznikUPrenosu().getRacunUcesnika());
//        analitika.setModelZaduzenja(nalog.getPodaciOPrenosu().getDuznikUPrenosu().getModelPrenosa());
//        analitika.setPozivNaBrojZaduzenja(nalog.getPodaciOPrenosu().getDuznikUPrenosu().getPozivNaBroj());
//        analitika.setRacunPoverioca(nalog.getPodaciOPrenosu().getPoverilacUPrenosu().getRacunUcesnika());
//        analitika.setModelOdobrenja(nalog.getPodaciOPrenosu().getPoverilacUPrenosu().getModelPrenosa());
//        analitika.setPozivNaBrojZaduzenja(nalog.getPodaciOPrenosu().getDuznikUPrenosu().getPozivNaBroj());
//        analitika.setPozivNaBrojOdobrenja(nalog.getPodaciOPrenosu().getPoverilacUPrenosu().getPozivNaBroj());
//        analitika.setIznos(nalog.getPodaciOPrenosu().getIznos());
//        analitika.setSifraValute(nalog.getPodaciOPrenosu().getOznakaValute().value());
//        analitika.setSvrhaPlacanja(nalog.getSvrhaPlacanja());
//        //analitikaDuznika.setDnevnoStanjeRacuna(repozitorijumDnevnoStanjeRacuna.findByRacun(racunDuznika));

        analitikaIzvodaRepository.save(analitika);
;

    }

}

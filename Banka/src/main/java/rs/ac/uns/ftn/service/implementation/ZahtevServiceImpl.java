package rs.ac.uns.ftn.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.exception.ServiceFaultException;
import rs.ac.uns.ftn.model.database.DnevnoStanjeRacuna;
import rs.ac.uns.ftn.model.database.Racun;
import rs.ac.uns.ftn.model.dto.error.ServiceFault;
import rs.ac.uns.ftn.model.dto.zahtev_za_izvod.ZahtevZaIzvod;
import rs.ac.uns.ftn.model.environment.EnvironmentProperties;
import rs.ac.uns.ftn.repository.DnevnoStanjeRacunaRepository;
import rs.ac.uns.ftn.repository.RacunRepository;
import rs.ac.uns.ftn.service.ZahtevService;

import java.util.Date;
import java.util.Optional;

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
    private EnvironmentProperties environmentProperties;

    @Override
    public void process(ZahtevZaIzvod zahtevZaIzvod) {
        String brojRacuna = zahtevZaIzvod.getBrojRacuna();
        Date datumZahteva = zahtevZaIzvod.getDatum().toGregorianCalendar().getTime();
        if(proveriRacun(brojRacuna)){
            Racun racun = racunRepository.findByBrojRacuna(brojRacuna).get();
            Optional<DnevnoStanjeRacuna> dnevnoStanjeRacuna = dnevnoStanjeRacunaRepository.findByRacunAndDatum(racun, datumZahteva);
            if(dnevnoStanjeRacuna.isPresent()){
                DnevnoStanjeRacuna dnevnoStanjeRacunaReal = dnevnoStanjeRacuna.get();
                //dobaviti analitike za ovo dnevno stanje
                //grupisati ih u neke preseke i eventualno preseke cuvati u bazi?
                //svaki presek ima svoj broj -> to je ono sto stize iz zahteva
            }else{
                throw new ServiceFaultException("Nije pronadjen.", new ServiceFault("404", "Nije pronadjeno dnevno stanje za racun u banci!"));
            }
        }else{
            throw new ServiceFaultException("Nije pronadjen.", new ServiceFault("404", "Racun zahteva nije pronadjen u banci!"));
        }
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

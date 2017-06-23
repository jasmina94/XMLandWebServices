package rs.ac.uns.ftn.repository;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.mt103.Mt103;
import rs.ac.uns.ftn.tipovi.TPodaciBanka;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jasmina on 23/06/2017.
 */
@Component
public class Mt103Repository {
    private static final Map<String, Mt103> mt103map = new HashMap<>();

    @PostConstruct
    public void initData(){
        Mt103 mt103 = new Mt103();
        Mt103.PodaciODuzniku duznik = new Mt103.PodaciODuzniku();
        Mt103.PodaciOPoveriocu poverioc = new Mt103.PodaciOPoveriocu();
        Mt103.PodaciOUplati uplata = new Mt103.PodaciOUplati();

        TPodaciBanka bankaDuznika = new TPodaciBanka();
        TPodaciBanka bankaPoverioca = new TPodaciBanka();

        bankaDuznika.setObracunskiRacun("847-1234567891123-56");
        bankaDuznika.setSwiftKod("12345678");

        bankaPoverioca.setObracunskiRacun("837-1274567891123-56");
        bankaPoverioca.setSwiftKod("12398678");

        duznik.setNaziv("MAXI D.o.o");
        duznik.setBrojRacuna("968-3216547896521-48");
        duznik.setPodaciOBanci(bankaDuznika);

        poverioc.setNaziv("Pionir D.O.O");


        mt103.setIdPoruke("blabla");
        mt103.setPodaciODuzniku(duznik);
        mt103.setPodaciOPoveriocu(poverioc);


        mt103map.put(mt103.getIdPoruke(), mt103);
    }

    public Mt103 findMt103(String idPoruke) {
        return mt103map.get(idPoruke);
    }
}

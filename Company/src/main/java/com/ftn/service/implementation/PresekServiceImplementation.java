package com.ftn.service.implementation;

import com.ftn.model.database.DnevnoStanjeRacuna;
import com.ftn.model.dto.AnalitikaIzvodaDTO;
import com.ftn.model.dto.DnevnoStanjeRacunaDTO;
import com.ftn.model.generated.presek.Presek;
import com.ftn.model.generated.stavkapreseka.StavkaPreseka;
import com.ftn.model.generated.zaglavljepreseka.ZaglavljePreseka;
import com.ftn.model.generated.zahtevzaizvod.ZahtevZaIzvod;
import com.ftn.repository.DnevnoStanjeRacunaDao;
import com.ftn.service.AnalitikaIzvodaService;
import com.ftn.service.DnevnoStanjeRacunaService;
import com.ftn.service.PresekService;
import com.ftn.service.ZahtevZaIzvodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Created by Olivera on 26.6.2017..
 */
@Service
public class PresekServiceImplementation implements PresekService {

    private final DnevnoStanjeRacunaService dnevnoStanjeRacunaService;
    private final AnalitikaIzvodaService analitikaIzvodaService;
    private final ZahtevZaIzvodService zahtevZaIzvodService;
    private final DnevnoStanjeRacunaDao dnevnoStanjeRacunaDao;
    private static int brojac;

    @Autowired
    public PresekServiceImplementation(DnevnoStanjeRacunaService dnevnoStanjeRacunaService, AnalitikaIzvodaService analitikaIzvodaService, ZahtevZaIzvodService zahtevZaIzvodService, DnevnoStanjeRacunaDao dnevnoStanjeRacunaDao) {
        this.dnevnoStanjeRacunaService = dnevnoStanjeRacunaService;
        this.analitikaIzvodaService = analitikaIzvodaService;
        this.zahtevZaIzvodService = zahtevZaIzvodService;
        this.dnevnoStanjeRacunaDao = dnevnoStanjeRacunaDao;
    }

    @Override
    public void process(Presek presek) {
        ZaglavljePreseka zaglavljePreseka = presek.getZaglavljePreseka();
        Optional<DnevnoStanjeRacuna> dnevnoStanjeRacuna = dnevnoStanjeRacunaDao.findByDatum(zaglavljePreseka.getDatumNaloga());
        DnevnoStanjeRacunaDTO sacuvanoDnevnoStanje;

        if(!dnevnoStanjeRacuna.isPresent()) {
            DnevnoStanjeRacunaDTO dnevnoStanjeRacunaDTO = new DnevnoStanjeRacunaDTO();
            dnevnoStanjeRacunaDTO.setBrojPreseka(zaglavljePreseka.getBrojPreseka().intValue());
            dnevnoStanjeRacunaDTO.setDatum(zaglavljePreseka.getDatumNaloga());
            dnevnoStanjeRacunaDTO.setPredhodnoStanje(zaglavljePreseka.getPrethodnoStanje().doubleValue());
            dnevnoStanjeRacunaDTO.setBrojPromenaNaTeret(zaglavljePreseka.getTeret().getBrojPromena().intValue());
            dnevnoStanjeRacunaDTO.setUkupnoNaTeret(zaglavljePreseka.getTeret().getUkupno().doubleValue());
            dnevnoStanjeRacunaDTO.setBrojPromenaUKorist(zaglavljePreseka.getKorist().getBrojPromena().intValue());
            dnevnoStanjeRacunaDTO.setUkupnoUKorist(zaglavljePreseka.getKorist().getUkupno().doubleValue());
            dnevnoStanjeRacunaDTO.setNovoStanje(zaglavljePreseka.getNovoStanje().doubleValue());

            sacuvanoDnevnoStanje = dnevnoStanjeRacunaService.create(dnevnoStanjeRacunaDTO);

            this.refreshBrojac();
        } else {
            sacuvanoDnevnoStanje = new DnevnoStanjeRacunaDTO(dnevnoStanjeRacuna.get());
        }

        List<StavkaPreseka> stavkePreseka = presek.getStavkaPreseka();

        for (StavkaPreseka stavka: stavkePreseka) {
            AnalitikaIzvodaDTO analitikaIzvodaDTO  = new AnalitikaIzvodaDTO();
            analitikaIzvodaDTO.setDatumNaloga(stavka.getPodaciOUplati().getDatumNaloga());
            if (stavka.getPodaciOUplati().getSmer().equalsIgnoreCase("K"))
                analitikaIzvodaDTO.setSmer(true);
            else
                analitikaIzvodaDTO.setSmer(false);

            analitikaIzvodaDTO.setDuznik(stavka.getPodaciODuzniku().getNaziv());
            analitikaIzvodaDTO.setPoverilac(stavka.getPodaciOPoveriocu().getNaziv());
            analitikaIzvodaDTO.setSvrhaPlacanja(stavka.getPodaciOUplati().getSvrhaPlacanja());
            analitikaIzvodaDTO.setDatumValute(stavka.getPodaciOUplati().getDatumValute());
            analitikaIzvodaDTO.setRacunDuznika(stavka.getPodaciODuzniku().getBrojRacuna());
            analitikaIzvodaDTO.setModelZaduzenja(stavka.getPodaciOUplati().getPodaciOZaduzenju().getModel().longValue());
            analitikaIzvodaDTO.setPozivNaBrojZaduzenja(stavka.getPodaciOUplati().getPodaciOZaduzenju().getPozivNaBroj());
            analitikaIzvodaDTO.setRacunPoverioca(stavka.getPodaciOPoveriocu().getBrojRacuna());
            analitikaIzvodaDTO.setModelOdobrenja(stavka.getPodaciOUplati().getPodaciOOdobrenju().getModel().longValue());
            analitikaIzvodaDTO.setPozivNaBrojOdobrenja(stavka.getPodaciOUplati().getPodaciOOdobrenju().getPozivNaBroj());
            analitikaIzvodaDTO.setIznos(stavka.getPodaciOUplati().getIznos());
            //?
            analitikaIzvodaDTO.setDnevnoStanjeRacuna(sacuvanoDnevnoStanje);
            AnalitikaIzvodaDTO kreiranaAnalitikaDTO = analitikaIzvodaService.create(analitikaIzvodaDTO);
            //sacuvanoDnevnoStanje.getAnalitikeIzvoda().add(kreiranaAnalitikaDTO);
            //dnevnoStanjeRacunaService.update(sacuvanoDnevnoStanje.getId(), sacuvanoDnevnoStanje);

        }


         if (brojac <= zaglavljePreseka.getBrojPreseka().intValue()) {
             ZahtevZaIzvod zahtevZaIzvod = new ZahtevZaIzvod();
             zahtevZaIzvod.setDatum(zaglavljePreseka.getDatumNaloga());
             zahtevZaIzvod.setBrojRacuna(zaglavljePreseka.getBrojRacuna());
             zahtevZaIzvod.setRedniBrojPreseka(BigInteger.valueOf(brojac));
             brojac += 1;
             zahtevZaIzvodService.posaljiZahtev(zahtevZaIzvod);
         }

    }

    private void refreshBrojac() {
        this.brojac = 2;
    }
}

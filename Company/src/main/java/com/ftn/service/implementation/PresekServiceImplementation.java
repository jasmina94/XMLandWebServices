package com.ftn.service.implementation;

import com.ftn.model.dto.AnalitikaIzvodaDTO;
import com.ftn.model.dto.DnevnoStanjeRacunaDTO;
import com.ftn.model.generated.presek.Presek;
import com.ftn.model.generated.stavkapreseka.StavkaPreseka;
import com.ftn.model.generated.zaglavljepreseka.ZaglavljePreseka;
import com.ftn.repository.DnevnoStanjeRacunaDao;
import com.ftn.service.AnalitikaIzvodaService;
import com.ftn.service.DnevnoStanjeRacunaService;
import com.ftn.service.PresekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Olivera on 26.6.2017..
 */
@Service
public class PresekServiceImplementation implements PresekService {

    private DnevnoStanjeRacunaService dnevnoStanjeRacunaService;
    private AnalitikaIzvodaService analitikaIzvodaService;

    @Autowired
    public PresekServiceImplementation(DnevnoStanjeRacunaService dnevnoStanjeRacunaService, AnalitikaIzvodaService analitikaIzvodaService) {
        this.dnevnoStanjeRacunaService = dnevnoStanjeRacunaService;
        this.analitikaIzvodaService = analitikaIzvodaService;
    }

    @Override
    public void process(Presek presek) {
        ZaglavljePreseka zaglavljePreseka = presek.getZaglavljePreseka();
        DnevnoStanjeRacunaDTO dnevnoStanjeRacunaDTO = new DnevnoStanjeRacunaDTO();
        dnevnoStanjeRacunaDTO.setBrojPreseka(zaglavljePreseka.getBrojPreseka().intValue());
        dnevnoStanjeRacunaDTO.setDatum(zaglavljePreseka.getDatumNaloga());
        dnevnoStanjeRacunaDTO.setPredhodnoStanje(zaglavljePreseka.getPrethodnoStanje().doubleValue());
        dnevnoStanjeRacunaDTO.setBrojPromenaNaTeret(zaglavljePreseka.getTeret().getBrojPromena().intValue());
        dnevnoStanjeRacunaDTO.setUkupnoNaTeret(zaglavljePreseka.getTeret().getUkupno().doubleValue());
        dnevnoStanjeRacunaDTO.setBrojPromenaUKorist(zaglavljePreseka.getKorist().getBrojPromena().intValue());
        dnevnoStanjeRacunaDTO.setUkupnoUKorist(zaglavljePreseka.getKorist().getUkupno().doubleValue());
        dnevnoStanjeRacunaDTO.setNovoStanje(zaglavljePreseka.getNovoStanje().doubleValue());

         DnevnoStanjeRacunaDTO sacuvanoDnevnoStanje = dnevnoStanjeRacunaService.create(dnevnoStanjeRacunaDTO);

         List<StavkaPreseka> stavkePreseka = presek.getStavkaPreseka();
        
         for (StavkaPreseka stavka: stavkePreseka) {
             AnalitikaIzvodaDTO analitikaIzvodaDTO  = new AnalitikaIzvodaDTO();
             analitikaIzvodaDTO.setDatumNaloga(stavka.getPodaciOUplati().getDatumNaloga());
             if (stavka.getPodaciOUplati().getSmer().equalsIgnoreCase("ulaz"))
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

             //?
             analitikaIzvodaDTO.setDnevnoStanjeRacuna(sacuvanoDnevnoStanje);
             AnalitikaIzvodaDTO kreiranaAnalitikaDTO = analitikaIzvodaService.create(analitikaIzvodaDTO);
             sacuvanoDnevnoStanje.getAnalitikeIzvoda().add(kreiranaAnalitikaDTO);
             dnevnoStanjeRacunaService.update(sacuvanoDnevnoStanje.getId(), sacuvanoDnevnoStanje);

         }

    }
}

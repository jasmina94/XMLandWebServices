package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.dto.FakturaDTO;
import com.ftn.model.dto.TStavkaFakturaDTO;
import com.ftn.model.environment.EnvironmentProperties;
import com.ftn.model.generated.faktura.Faktura;
import com.ftn.model.generated.tipovi.TStavkaFaktura;
import com.ftn.repository.FakturaDao;
import com.ftn.repository.TPodaciSubjekatDao;
import com.ftn.service.FakturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by JELENA on 19.6.2017.
 */

@Service
public class FakturaServiceImplementation implements FakturaService {


    private final FakturaDao fakturaDao;
    private final TPodaciSubjekatDao tPodaciSubjekatDao;
    private final EnvironmentProperties environmentProperties;

    @Autowired
    public FakturaServiceImplementation(FakturaDao fakturaDao, TPodaciSubjekatDao tPodaciSubjekatDao, EnvironmentProperties environmentProperties) {
        this.fakturaDao = fakturaDao;
        this.tPodaciSubjekatDao = tPodaciSubjekatDao;
        this.environmentProperties = environmentProperties;
    }

    @Override
    public List<FakturaDTO> read() {
        return fakturaDao.findAll().stream().map(FakturaDTO::new).collect(Collectors.toList());
    }

    @Override
    public FakturaDTO readFaktura(Long id) {
        return new FakturaDTO(fakturaDao.findById(id).get());
    }

    @Override
    public List<FakturaDTO> readDobavljac() {
        List<Faktura> faktureFirme = new ArrayList<>();
        for(Faktura faktura : fakturaDao.findAll()) {
            if(faktura.getPodaciODobavljacu().getPib().equals(environmentProperties.getPib())) {
                faktureFirme.add(faktura);
            }
        }
        return faktureFirme.stream().map(FakturaDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<FakturaDTO> readKupac() {
        List<Faktura> faktureFirme = new ArrayList<>();
        for(Faktura faktura : fakturaDao.findAll()) {
            if(faktura.getPodaciOKupcu().getPib().equals(environmentProperties.getPib())) {
                faktureFirme.add(faktura);
            }
        }
        return faktureFirme.stream().map(FakturaDTO::new).collect(Collectors.toList());
    }

    @Override
    public FakturaDTO create(FakturaDTO fakturaDTO) {
        if (fakturaDao.findById(fakturaDTO.getId()).isPresent())
            throw new BadRequestException();

        final Faktura faktura = fakturaDTO.construct();

        if (!tPodaciSubjekatDao.findById(fakturaDTO.getPodaciOKupcu().getId()).isPresent() || !tPodaciSubjekatDao.findById(fakturaDTO.getPodaciODobavljacu().getId()).isPresent())
            throw new BadRequestException();

        faktura.setPodaciOKupcu((tPodaciSubjekatDao.findById(fakturaDTO.getPodaciOKupcu().getId()).get()));
        faktura.setPodaciODobavljacu((tPodaciSubjekatDao.findById(fakturaDTO.getPodaciODobavljacu().getId()).get()));

        ArrayList<TStavkaFaktura> stavke = new ArrayList<>();
        for (TStavkaFakturaDTO stavkaDTO: fakturaDTO.getStavkaFakture())
            stavke.add(stavkaDTO.construct());
        faktura.setStavkaFakture(stavke);

        fakturaDao.save(faktura);
        return new FakturaDTO(faktura);
    }

    @Override
    public FakturaDTO update(Long id, FakturaDTO fakturaDTO) {

        BigDecimal vrednostRobe = BigDecimal.valueOf(0.0);
        BigDecimal vrednostUsluga = BigDecimal.valueOf(0.0);
        BigDecimal vrednostRobaIUsluga = BigDecimal.valueOf(0.0);
        BigDecimal ukupanRabat= BigDecimal.valueOf(0.0);
        BigDecimal ukupanPorez = BigDecimal.valueOf(0.0);


        for (TStavkaFakturaDTO stavka: fakturaDTO.getStavkaFakture()) {
            if (stavka.isRoba())
                vrednostRobe.add(stavka.getVrednost());
            else
                vrednostUsluga.add(stavka.getVrednost());

            vrednostRobaIUsluga.add(stavka.getVrednost());
            ukupanRabat.add(stavka.getIznosRabata());
            ukupanPorez.add(stavka.getUkupanPorez());
        }

        fakturaDTO.setVrednostRobe(vrednostRobe);
        fakturaDTO.setVrednostUsluga(vrednostUsluga);
        fakturaDTO.setUkupnoRobaIUsluga(vrednostRobaIUsluga);
        fakturaDTO.setUkupanRabat(ukupanRabat);
        fakturaDTO.setUkupanPorez(ukupanPorez);
        fakturaDTO.setIznosZaUplatu(vrednostRobaIUsluga.add(ukupanPorez).subtract(ukupanRabat));
        fakturaDTO.setUplataNaRacun(fakturaDTO.getPodaciODobavljacu().getRacunFirme());

        final Faktura faktura = fakturaDao.findById(id).orElseThrow(NotFoundException::new);
        faktura.merge(fakturaDTO);
        fakturaDao.save(faktura);
        return new FakturaDTO(faktura);
    }
}

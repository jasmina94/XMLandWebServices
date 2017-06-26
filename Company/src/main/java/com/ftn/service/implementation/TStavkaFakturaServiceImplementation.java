package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.model.dto.FakturaDTO;
import com.ftn.model.dto.RobaUslugaDTO;
import com.ftn.model.dto.TStavkaFakturaDTO;
import com.ftn.model.generated.faktura.Faktura;
import com.ftn.model.generated.tipovi.TStavkaFaktura;
import com.ftn.repository.FakturaDao;
import com.ftn.repository.TStavkaFakturaDao;
import com.ftn.service.FakturaService;
import com.ftn.service.TStavkaFakturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Olivera on 20.6.2017..
 */
@Service
public class TStavkaFakturaServiceImplementation implements TStavkaFakturaService {


    private final TStavkaFakturaDao tStavkaFakturaDao;
    private final FakturaDao fakturaDao;
    private final FakturaService fakturaService;

    @Autowired
    public TStavkaFakturaServiceImplementation(TStavkaFakturaDao tStavkaFakturaDao, FakturaDao fakturaDao, FakturaService fakturaService) {
        this.tStavkaFakturaDao = tStavkaFakturaDao;
        this.fakturaDao = fakturaDao;
        this.fakturaService = fakturaService;
    }

    @Override
    public List<TStavkaFakturaDTO> read() {
        return tStavkaFakturaDao.findAll().stream().map(TStavkaFakturaDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<TStavkaFakturaDTO> read(Long fakturaId) {
        return null;
    }

    @Override
    public TStavkaFakturaDTO create(TStavkaFakturaDTO tStavkaFakturaDTO) {
        if (tStavkaFakturaDao.findById(tStavkaFakturaDTO.getId()).isPresent())
            throw new BadRequestException();

        //System.out.println("Prreeeeeeee" + tStavkaFakturaDTO);
        final TStavkaFaktura tStavkaFaktura = tStavkaFakturaDTO.construct();
        //System.out.println("BLAAAA:" + tStavkaFaktura);
        tStavkaFakturaDao.save(tStavkaFaktura);
        return new TStavkaFakturaDTO(tStavkaFaktura);
    }

    public TStavkaFakturaDTO kreirajStavku(FakturaDTO fakturaDTO, BigDecimal kolicina, RobaUslugaDTO robaUslugaDTO) {
        System.out.println("SERVIC" + fakturaDTO);
        Faktura faktura = fakturaDao.findById(fakturaDTO.getId()).get();
       // System.out.println(faktura);

        TStavkaFakturaDTO tStavkaFakturaDTO = new TStavkaFakturaDTO();
        //System.out.println("redniBroj " + (fakturaDTO.getStavkaFakture().size() + 1));
        tStavkaFakturaDTO.setRedniBroj(fakturaDTO.getStavkaFakture().size() + 1);
        tStavkaFakturaDTO.setNazivRobeUsluge(robaUslugaDTO.getNaziv());
        tStavkaFakturaDTO.setKolicina(kolicina);
        tStavkaFakturaDTO.setJedinicaMere(robaUslugaDTO.getJedinicaMere());
        tStavkaFakturaDTO.setJedinicnaCena(robaUslugaDTO.getCena());
        //ovi atributi da se postave u skadu sa poslovnom logikom
        tStavkaFakturaDTO.setVrednost(tStavkaFakturaDTO.getJedinicnaCena().multiply(tStavkaFakturaDTO.getKolicina()));
        tStavkaFakturaDTO.setProcenatRabata(robaUslugaDTO.getProcenatRabata());
        tStavkaFakturaDTO.setIznosRabata(BigDecimal.valueOf(0.0));
        tStavkaFakturaDTO.setUmanjenoZaRabat(BigDecimal.valueOf(0.0));
        tStavkaFakturaDTO.setUkupanPorez(BigDecimal.valueOf(0.0));
       // tStavkaFakturaDTO.setFaktura(fakturaDTO);
        TStavkaFakturaDTO kreiranaStavkaFakturaDTO = create(tStavkaFakturaDTO);
       // System.out.println("redni broj sacuvan " + kreiranaStavkaFakturaDTO.getRedniBroj());
        fakturaDTO.getStavkaFakture().add(kreiranaStavkaFakturaDTO);
        //System.out.println("nakon dodavanja" + fakturaDTO.getStavkaFakture().size());
        //fakturaDao.save(fakturaDTO.construct());
        System.out.println("pre update");
        fakturaService.update(fakturaDTO.getId(), fakturaDTO);
        System.out.println("posle update");


        return kreiranaStavkaFakturaDTO;
    }
}

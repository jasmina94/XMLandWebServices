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
        Faktura faktura = fakturaDao.findById(fakturaDTO.getId()).get();

        TStavkaFakturaDTO tStavkaFakturaDTO = new TStavkaFakturaDTO();
        tStavkaFakturaDTO.setRedniBroj(fakturaDTO.getStavkaFakture().size() + 1);
        tStavkaFakturaDTO.setNazivRobeUsluge(robaUslugaDTO.getNaziv());
        tStavkaFakturaDTO.setRoba(!robaUslugaDTO.isTip());
        tStavkaFakturaDTO.setKolicina(kolicina);
        tStavkaFakturaDTO.setJedinicaMere(robaUslugaDTO.getJedinicaMere());
        tStavkaFakturaDTO.setJedinicnaCena(robaUslugaDTO.getCena());

        tStavkaFakturaDTO.setVrednost(tStavkaFakturaDTO.getJedinicnaCena().multiply(tStavkaFakturaDTO.getKolicina()));
        tStavkaFakturaDTO.setProcenatRabata(robaUslugaDTO.getProcenatRabata());
        tStavkaFakturaDTO.setIznosRabata(tStavkaFakturaDTO.getVrednost().multiply(robaUslugaDTO.getProcenatRabata().divide(BigDecimal.valueOf(100.00))));
        tStavkaFakturaDTO.setUmanjenoZaRabat(tStavkaFakturaDTO.getVrednost().subtract(tStavkaFakturaDTO.getIznosRabata()));
        tStavkaFakturaDTO.setUkupanPorez(tStavkaFakturaDTO.getVrednost().multiply(robaUslugaDTO.getProcenatPoreza()).divide(BigDecimal.valueOf(100.00)));
        TStavkaFakturaDTO kreiranaStavkaFakturaDTO = create(tStavkaFakturaDTO);
        fakturaDTO.getStavkaFakture().add(kreiranaStavkaFakturaDTO);
        fakturaService.update(fakturaDTO.getId(), fakturaDTO);

        return kreiranaStavkaFakturaDTO;
    }
}

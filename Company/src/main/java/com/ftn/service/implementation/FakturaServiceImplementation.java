package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.Faktura;
import com.ftn.model.TPodaciSubjekt;
import com.ftn.model.dto.FakturaDTO;
import com.ftn.repository.FakturaDao;
import com.ftn.repository.TPodaciSubjekatDao;
import com.ftn.service.FakturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
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

    @Autowired
    public FakturaServiceImplementation(FakturaDao fakturaDao, TPodaciSubjekatDao tPodaciSubjekatDao) {
        this.fakturaDao = fakturaDao;
        this.tPodaciSubjekatDao = tPodaciSubjekatDao;
    };

    @Override
    public List<FakturaDTO> read() {
        return fakturaDao.findAll().stream().map(FakturaDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<FakturaDTO> read(String pib) {
        List<Faktura> faktureFirme = new ArrayList<>();
        for(Faktura faktura : fakturaDao.findAll()) {
            if(faktura.getPodaciODobavljacu().getPib().equals(pib) || faktura.getPodaciOKupcu().getPib().equals(pib)) {
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
        fakturaDao.save(faktura);
        return new FakturaDTO(faktura);
    }

    @Override
    public FakturaDTO update(Long id, FakturaDTO fakturaDTO) {
        final Faktura faktura = fakturaDao.findById(id).orElseThrow(NotFoundException::new);
        faktura.merge(fakturaDTO);
        fakturaDao.save(faktura);
        return new FakturaDTO(faktura);
    }
}

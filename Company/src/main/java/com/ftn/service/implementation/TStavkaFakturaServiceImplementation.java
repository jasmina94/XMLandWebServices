package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.model.TPodaciSubjekt;
import com.ftn.model.TStavkaFaktura;
import com.ftn.model.dto.TPodaciSubjektDTO;
import com.ftn.model.dto.TStavkaFakturaDTO;
import com.ftn.repository.TStavkaFakturaDao;
import com.ftn.service.TStavkaFakturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Olivera on 20.6.2017..
 */
@Service
public class TStavkaFakturaServiceImplementation implements TStavkaFakturaService {

    private final TStavkaFakturaDao tStavkaFakturaDao;

    @Autowired
    public TStavkaFakturaServiceImplementation(TStavkaFakturaDao tStavkaFakturaDao) {
        this.tStavkaFakturaDao = tStavkaFakturaDao;
    }

    @Override
    public List<TStavkaFakturaDTO> read() {
        return tStavkaFakturaDao.findAll().stream().map(TStavkaFakturaDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<TStavkaFakturaDTO> read(Long fakturaId) {
        return tStavkaFakturaDao.findByFakturaId(fakturaId).stream().map(TStavkaFakturaDTO::new).collect(Collectors.toList());
    }

    @Override
    public TStavkaFakturaDTO create(TStavkaFakturaDTO tStavkaFakturaDTO) {
        if (tStavkaFakturaDao.findById(tStavkaFakturaDTO.getId()).isPresent())
            throw new BadRequestException();

        final TStavkaFaktura tStavkaFaktura = tStavkaFakturaDTO.construct();
        tStavkaFakturaDao.save(tStavkaFaktura);
        return new TStavkaFakturaDTO(tStavkaFaktura);
    }
}

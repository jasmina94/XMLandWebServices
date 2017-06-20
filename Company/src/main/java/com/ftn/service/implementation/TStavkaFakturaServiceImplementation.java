package com.ftn.service.implementation;

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
}

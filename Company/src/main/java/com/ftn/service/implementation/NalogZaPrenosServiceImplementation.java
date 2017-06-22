package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.model.Faktura;
import com.ftn.model.NalogZaPrenos;
import com.ftn.model.TPodaciSubjekt;
import com.ftn.model.dto.NalogZaPrenosDTO;
import com.ftn.model.dto.TPodaciSubjektDTO;
import com.ftn.repository.NalogZaPrenosDao;
import com.ftn.service.NalogZaPrenosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Olivera on 22.6.2017..
 */
@Service
public class NalogZaPrenosServiceImplementation implements NalogZaPrenosService {

    private final NalogZaPrenosDao nalogZaPrenosDao;

    @Autowired
    public NalogZaPrenosServiceImplementation(NalogZaPrenosDao nalogZaPrenosDao) {
        this.nalogZaPrenosDao = nalogZaPrenosDao;
    }

    @Override
    public List<NalogZaPrenosDTO> read() {
        return nalogZaPrenosDao.findAll().stream().map(NalogZaPrenosDTO::new).collect(Collectors.toList());
    }

    @Override
    public NalogZaPrenosDTO create(NalogZaPrenosDTO nalogZaPrenosDTO) {
        if (nalogZaPrenosDao.findById(nalogZaPrenosDTO.getId()).isPresent())
            throw new BadRequestException();

        final NalogZaPrenos nalogZaPrenos = nalogZaPrenosDTO.construct();
        nalogZaPrenosDao.save(nalogZaPrenos);
        return new NalogZaPrenosDTO(nalogZaPrenos);
    }
}

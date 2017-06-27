package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.database.DnevnoStanjeRacuna;
import com.ftn.model.dto.DnevnoStanjeRacunaDTO;
import com.ftn.repository.DnevnoStanjeRacunaDao;
import com.ftn.service.DnevnoStanjeRacunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by JELENA on 27.6.2017.
 */
@Service
public class DnevnoStanjeRacunaImplementation implements DnevnoStanjeRacunaService {
    private final DnevnoStanjeRacunaDao dnevnoStanjeRacunaDao;

    @Autowired
    public DnevnoStanjeRacunaImplementation(DnevnoStanjeRacunaDao dnevnoStanjeRacunaDao) {
        this.dnevnoStanjeRacunaDao = dnevnoStanjeRacunaDao;
    }

    @Override
    public List<DnevnoStanjeRacunaDTO> read() {
        return dnevnoStanjeRacunaDao.findAll().stream().map(DnevnoStanjeRacunaDTO::new).collect(Collectors.toList());
    }

    @Override
    public DnevnoStanjeRacunaDTO create(DnevnoStanjeRacunaDTO dnevnoStanjeRacunaDTO) {
        if (dnevnoStanjeRacunaDao.findById(dnevnoStanjeRacunaDTO.getId()).isPresent())
            throw new BadRequestException();

        final DnevnoStanjeRacuna dnevnoStanjeRacuna = dnevnoStanjeRacunaDTO.construct();
        dnevnoStanjeRacunaDao.save(dnevnoStanjeRacuna);
        return new DnevnoStanjeRacunaDTO(dnevnoStanjeRacuna);
    }

    @Override
    public DnevnoStanjeRacunaDTO update(Long id, DnevnoStanjeRacunaDTO dnevnoStanjeRacunaDTO) {

        final DnevnoStanjeRacuna dnevnoStanjeRacuna = dnevnoStanjeRacunaDao.findById(id).orElseThrow(NotFoundException::new);
        dnevnoStanjeRacuna.merge(dnevnoStanjeRacunaDTO);
        dnevnoStanjeRacunaDao.save(dnevnoStanjeRacuna);
        return new DnevnoStanjeRacunaDTO(dnevnoStanjeRacuna);
    }
}

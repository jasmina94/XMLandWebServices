package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.database.AnalitikaIzvoda;
import com.ftn.model.dto.AnalitikaIzvodaDTO;
import com.ftn.repository.AnalitikaIzvodaDao;
import com.ftn.service.AnalitikaIzvodaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by JELENA on 27.6.2017.
 */
@Service
public class AnalitikaIzvodaServiceImplementation implements AnalitikaIzvodaService {
    private final AnalitikaIzvodaDao analitikaIzvodaDao;

    @Autowired
    public AnalitikaIzvodaServiceImplementation(AnalitikaIzvodaDao analitikaIzvodaDao) {
        this.analitikaIzvodaDao = analitikaIzvodaDao;
    }

    @Override
    public List<AnalitikaIzvodaDTO> read() {
        return analitikaIzvodaDao.findAll().stream().map(AnalitikaIzvodaDTO::new).collect(Collectors.toList());

    }

    @Override
    public AnalitikaIzvodaDTO create(AnalitikaIzvodaDTO analitikaIzvodaDTO) {
        if (analitikaIzvodaDao.findById(analitikaIzvodaDTO.getId()).isPresent())
            throw new BadRequestException();

        final AnalitikaIzvoda analitikaIzvoda = analitikaIzvodaDTO.construct();
        analitikaIzvodaDao.save(analitikaIzvoda);
        return new AnalitikaIzvodaDTO(analitikaIzvoda);
    }

}

package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.model.database.RobaUsluga;
import com.ftn.model.dto.RobaUslugaDTO;
import com.ftn.repository.RobaUslugaDao;
import com.ftn.service.RobaUslugaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by JELENA on 22.6.2017.
 */
@Service
public class RobaUslugaServiceImplementation implements RobaUslugaService {

    private final RobaUslugaDao robaUslugaDao;

    @Autowired
    public RobaUslugaServiceImplementation(RobaUslugaDao robaUslugaDao) {
        this.robaUslugaDao = robaUslugaDao;
    }

    @Override
    public List<RobaUslugaDTO> read() {
        return robaUslugaDao.findAll().stream().map(RobaUslugaDTO::new).collect(Collectors.toList());
    }

    @Override
    public RobaUslugaDTO create(RobaUslugaDTO robaUslugaDTO) {
        if (robaUslugaDao.findById(robaUslugaDTO.getId()).isPresent())
            throw new BadRequestException();

        final RobaUsluga robaUsluga = robaUslugaDTO.construct();
        robaUslugaDao.save(robaUsluga);
        return new RobaUslugaDTO(robaUsluga);    }
}

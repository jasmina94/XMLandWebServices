package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.model.dto.TPodaciSubjektDTO;
import com.ftn.model.generated.tipovi.TPodaciSubjekt;
import com.ftn.repository.TPodaciSubjekatDao;
import com.ftn.service.TPodaciSubjektService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by JELENA on 19.6.2017.
 */
@Service
public class TPodaciSubjektServiceImplementation implements TPodaciSubjektService {


    private final TPodaciSubjekatDao tPodaciSubjekatDao;

    @Autowired
    public  TPodaciSubjektServiceImplementation(TPodaciSubjekatDao tPodaciSubjekatDao) { this.tPodaciSubjekatDao = tPodaciSubjekatDao; }

    @Override
    public List<TPodaciSubjektDTO> read() {
        return tPodaciSubjekatDao.findAll().stream().map(TPodaciSubjektDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<TPodaciSubjektDTO> readPoslovniPartneri(Long id) {
        List<TPodaciSubjekt> poslovniPartneri = new ArrayList<>();
        for(TPodaciSubjekt partner : tPodaciSubjekatDao.findAll()) {
            if (partner.getId() == id) {
                    poslovniPartneri = partner.getPoslovniPartneri();
            }
        }
        return poslovniPartneri.stream().map(TPodaciSubjektDTO::new).collect(Collectors.toList());
    }

    @Override
    public TPodaciSubjektDTO create(TPodaciSubjektDTO tPodaciSubjektDTO) {
        if (tPodaciSubjekatDao.findById(tPodaciSubjektDTO.getId()).isPresent())
            throw new BadRequestException();

        final TPodaciSubjekt tPodaciSubjekt = tPodaciSubjektDTO.construct();
        tPodaciSubjekatDao.save(tPodaciSubjekt);
        return new TPodaciSubjektDTO(tPodaciSubjekt);
    }


}

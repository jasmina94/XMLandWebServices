package com.ftn.service;

import com.ftn.model.dto.TPodaciSubjektDTO;

import java.util.List;
import java.util.Set;

/**
 * Created by JELENA on 19.6.2017.
 */
public interface TPodaciSubjektService {

    List<TPodaciSubjektDTO> read();

    List<TPodaciSubjektDTO> readPoslovniPartneri(Long id);

    TPodaciSubjektDTO create(TPodaciSubjektDTO tPodaciSubjektDTO);
}

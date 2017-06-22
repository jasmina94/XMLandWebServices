package com.ftn.service;

import com.ftn.model.dto.TPodaciSubjektDTO;

import java.util.List;

/**
 * Created by JELENA on 19.6.2017.
 */
public interface TPodaciSubjektService {

    List<TPodaciSubjektDTO> read();

    TPodaciSubjektDTO create(TPodaciSubjektDTO tPodaciSubjektDTO);
}

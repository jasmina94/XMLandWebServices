package com.ftn.service;

import com.ftn.model.dto.FakturaDTO;

import java.util.List;

/**
 * Created by JELENA on 19.6.2017.
 */
public interface FakturaService {

    List<FakturaDTO> read();

    FakturaDTO readFaktura(Long id);

    List<FakturaDTO> readDobavljac();

    List<FakturaDTO> readKupac();

    FakturaDTO create(FakturaDTO fakturaDTO);

    FakturaDTO update(Long id, FakturaDTO fakturaDTO);

}

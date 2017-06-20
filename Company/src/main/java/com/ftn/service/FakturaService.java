package com.ftn.service;

import com.ftn.model.Zaposleni;
import com.ftn.model.dto.FakturaDTO;
import com.ftn.model.dto.ZaposleniDTO;

import java.util.List;

/**
 * Created by JELENA on 19.6.2017.
 */
public interface FakturaService {

    List<FakturaDTO> read();

    List<FakturaDTO> read(String pib);

    FakturaDTO create(FakturaDTO fakturaDTO);

    FakturaDTO update(Long id, FakturaDTO fakturaDTO);

}

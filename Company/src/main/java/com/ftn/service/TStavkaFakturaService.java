package com.ftn.service;

import com.ftn.model.dto.FakturaDTO;
import com.ftn.model.dto.RobaUslugaDTO;
import com.ftn.model.dto.TStavkaFakturaDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Olivera on 20.6.2017..
 */
public interface TStavkaFakturaService {

    List<TStavkaFakturaDTO> read();

    List<TStavkaFakturaDTO> read(Long fakturaID);

    TStavkaFakturaDTO create(TStavkaFakturaDTO tStavkaFakturaDTO);

    TStavkaFakturaDTO kreirajStavku(FakturaDTO fakturaDTO, BigDecimal kolicina, RobaUslugaDTO robaUslugaDTO);
}

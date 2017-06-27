package com.ftn.service;

import com.ftn.model.dto.AnalitikaIzvodaDTO;
import com.ftn.model.dto.FakturaDTO;

import java.util.List;

/**
 * Created by JELENA on 27.6.2017.
 */
public interface AnalitikaIzvodaService {

    List<AnalitikaIzvodaDTO> read();

    AnalitikaIzvodaDTO create(AnalitikaIzvodaDTO analitikaIzvodaDTO);


}

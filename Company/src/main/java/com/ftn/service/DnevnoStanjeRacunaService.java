package com.ftn.service;

import com.ftn.model.dto.DnevnoStanjeRacunaDTO;
import com.ftn.model.dto.FakturaDTO;

import java.util.List;

/**
 * Created by JELENA on 27.6.2017.
 */
public interface DnevnoStanjeRacunaService {

    List<DnevnoStanjeRacunaDTO> read();

    DnevnoStanjeRacunaDTO create(DnevnoStanjeRacunaDTO dnevnoStanjeRacunaDTO);

    DnevnoStanjeRacunaDTO update(Long id, DnevnoStanjeRacunaDTO dnevnoStanjeRacunaDTO);
}

package com.ftn.service;

import com.ftn.model.dto.RobaUslugaDTO;

import java.util.List;

/**
 * Created by JELENA on 22.6.2017.
 */
public interface RobaUslugaService {

    List<RobaUslugaDTO> read();

    RobaUslugaDTO create(RobaUslugaDTO robaUslugaDTO);


}

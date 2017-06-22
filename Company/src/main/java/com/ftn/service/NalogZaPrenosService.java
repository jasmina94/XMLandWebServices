package com.ftn.service;

import com.ftn.model.dto.NalogZaPrenosDTO;

import java.util.List;

/**
 * Created by Olivera on 22.6.2017..
 */
public interface NalogZaPrenosService {

    List<NalogZaPrenosDTO> read();

    NalogZaPrenosDTO create(NalogZaPrenosDTO nalogZaPrenosDTO);
}

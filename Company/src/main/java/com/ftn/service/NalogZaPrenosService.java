package com.ftn.service;

import com.ftn.model.dto.NalogZaPrenosDTO;
import com.ftn.model.dto.PodaciZaNalogDTO;

import java.util.List;

/**
 * Created by Olivera on 22.6.2017..
 */
public interface NalogZaPrenosService {

    List<NalogZaPrenosDTO> read();

    List<NalogZaPrenosDTO> readDuznik(String naziv);

    NalogZaPrenosDTO create(NalogZaPrenosDTO nalogZaPrenosDTO);

    NalogZaPrenosDTO kreirajNalog(PodaciZaNalogDTO podaciZaNalogDTO);
}

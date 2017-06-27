package rs.ac.uns.ftn.service;

import rs.ac.uns.ftn.model.dto.nalog_za_prenos.NalogZaPrenos;

/**
 * Created by zlatan on 6/24/17.
 */
public interface PlacanjeService {

    void process(NalogZaPrenos nalogZaPrenos);

}

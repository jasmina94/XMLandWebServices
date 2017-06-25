package rs.ac.uns.ftn.service;

import rs.ac.uns.ftn.model.dto.nalog_za_prenos.NalogZaPrenos;

/**
 * Created by zlatan on 6/25/17.
 */
public interface ClearingService {

    void process(NalogZaPrenos nalogZaPrenos);

    void send(NalogZaPrenos nalogZaPrenos);
}

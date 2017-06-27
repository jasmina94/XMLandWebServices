package rs.ac.uns.ftn.service;

import rs.ac.uns.ftn.model.dto.zahtev_za_izvod.ZahtevZaIzvod;

/**
 * Created by zlatan on 26/06/2017.
 */
public interface ZahtevService {

    void process(ZahtevZaIzvod zahtevZaIzvod);
}

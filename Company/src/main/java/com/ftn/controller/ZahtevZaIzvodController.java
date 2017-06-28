package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.model.database.DnevnoStanjeRacuna;
import com.ftn.model.generated.zahtevzaizvod.ZahtevZaIzvod;
import com.ftn.repository.DnevnoStanjeRacunaDao;
import com.ftn.service.ZahtevZaIzvodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Created by Olivera on 26.6.2017..
 */
@RestController
@RequestMapping("/api/zahtevZaIzvod")
public class ZahtevZaIzvodController {

    private final ZahtevZaIzvodService zahtevZaIzvodService;

    private final DnevnoStanjeRacunaDao dnevnoStanjeRacunaDao;

    @Autowired
    public ZahtevZaIzvodController(ZahtevZaIzvodService zahtevZaIzvodService, DnevnoStanjeRacunaDao dnevnoStanjeRacunaDao) {
        this.zahtevZaIzvodService = zahtevZaIzvodService;
        this.dnevnoStanjeRacunaDao = dnevnoStanjeRacunaDao;
    }

    @PostMapping(value = "/posaljiZahtev")
    public ResponseEntity posaljiZahtev(@RequestBody ZahtevZaIzvod zahtevZaIzvod) {
        final Optional<DnevnoStanjeRacuna> dnevnoStanjeRacuna = dnevnoStanjeRacunaDao.findByDatum(zahtevZaIzvod.getDatum());
        dnevnoStanjeRacuna.ifPresent(dnevnoStanjeRacunaDao::delete);
        return new ResponseEntity<>(zahtevZaIzvodService.posaljiZahtev(zahtevZaIzvod), HttpStatus.OK);
    }
}

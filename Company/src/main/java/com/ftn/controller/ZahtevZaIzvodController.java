package com.ftn.controller;

import com.ftn.model.generated.zahtevzaizvod.ZahtevZaIzvod;
import com.ftn.service.ZahtevZaIzvodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Olivera on 26.6.2017..
 */
@RestController
@RequestMapping("/api/zahtevZaIzvod")
public class ZahtevZaIzvodController {

    private final ZahtevZaIzvodService zahtevZaIzvodService;

    @Autowired
    public ZahtevZaIzvodController(ZahtevZaIzvodService zahtevZaIzvodService) {
        this.zahtevZaIzvodService = zahtevZaIzvodService;
    }

    @PostMapping(value = "/posaljiZahtev")
    public ResponseEntity posaljiZahtev(@RequestBody ZahtevZaIzvod zahtevZaIzvod) {
        return new ResponseEntity<>(zahtevZaIzvodService.posaljiZahtev(zahtevZaIzvod), HttpStatus.OK);
    }

}

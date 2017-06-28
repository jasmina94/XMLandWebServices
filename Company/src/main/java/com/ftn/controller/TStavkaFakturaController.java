package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.exception.BadRequestException;
import com.ftn.model.dto.FakturaDTO;
import com.ftn.model.dto.RobaUslugaDTO;
import com.ftn.model.dto.TStavkaFakturaDTO;
import com.ftn.service.FakturaService;
import com.ftn.service.TStavkaFakturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * Created by Olivera on 20.6.2017..
 */
@RestController
@RequestMapping("/api/stavkeFakture")
public class TStavkaFakturaController {

    private final TStavkaFakturaService tStavkaFakturaService;
    private final FakturaService fakturaService;

    @Autowired
    public TStavkaFakturaController(TStavkaFakturaService tStavkaFakturaService, FakturaService fakturaService) {
        this.tStavkaFakturaService = tStavkaFakturaService;
        this.fakturaService = fakturaService;
    }

    @Transactional
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(tStavkaFakturaService.read(), HttpStatus.OK);
    }

    @Transactional
    @GetMapping(value = "/fakture/{fakturaId}")
    public ResponseEntity read(@PathVariable Long fakturaId) {
        return new ResponseEntity<>(tStavkaFakturaService.read(fakturaId), HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/{fakturaId}/{kolicina}")
    public ResponseEntity createTStavkaFaktura(@PathVariable Long fakturaId, @PathVariable BigDecimal kolicina, @Valid @RequestBody RobaUslugaDTO robaUslugaDTO, BindingResult bindingResult) {
        FakturaDTO fakturaDTO = fakturaService.readFaktura(fakturaId);
        //tStavkaFakturaService.kreirajStavku(fakturaDTO, kolicina, robaUslugaDTO);
        System.out.println("faktura id " + fakturaId + "kolicina " + kolicina + "roba " + robaUslugaDTO.getNaziv());
        if (bindingResult.hasErrors())
            throw new BadRequestException();

        return new ResponseEntity<>(tStavkaFakturaService.kreirajStavku(fakturaDTO, kolicina, robaUslugaDTO), HttpStatus.OK);


    }
}

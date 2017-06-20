package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.service.TStavkaFakturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

/**
 * Created by Olivera on 20.6.2017..
 */
@RestController
@RequestMapping("/api/stavkeFakture")
public class TStavkaFakturaController {

    private final TStavkaFakturaService tStavkaFakturaService;

    @Autowired
    public TStavkaFakturaController(TStavkaFakturaService tStavkaFakturaService) {
        this.tStavkaFakturaService = tStavkaFakturaService;
    }

    @Transactional
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(tStavkaFakturaService.read(), HttpStatus.OK);
    }

    @Transactional
    @GetMapping(value = "/fakture/{fakturaId}")
    public ResponseEntity read(@PathVariable Long fakturaId) {
        System.out.print(fakturaId);
        return new ResponseEntity<>(tStavkaFakturaService.read(fakturaId), HttpStatus.OK);
    }
}

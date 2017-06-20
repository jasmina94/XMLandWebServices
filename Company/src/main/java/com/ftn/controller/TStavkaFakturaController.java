package com.ftn.controller;

import com.ftn.service.TStavkaFakturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}

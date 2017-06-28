package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.exception.BadRequestException;
import com.ftn.model.dto.NalogZaPrenosDTO;
import com.ftn.model.dto.PodaciZaNalogDTO;
import com.ftn.service.NalogZaPrenosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * Created by Olivera on 22.6.2017..
 */
@RestController
@RequestMapping("api/naloziZaPrenos")
public class NalogZaPrenosController {

    private final NalogZaPrenosService nalogZaPrenosService;

    @Autowired
    public NalogZaPrenosController(NalogZaPrenosService nalogZaPrenosService) {
        this.nalogZaPrenosService = nalogZaPrenosService;
    }

    @Transactional
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(nalogZaPrenosService.read(), HttpStatus.OK);
    }

    @Transactional
    @GetMapping(value = "/firmaDuznik/{naziv}/")
    public ResponseEntity readDuznik(@PathVariable String naziv) {
        return new ResponseEntity<>(nalogZaPrenosService.readDuznik(naziv), HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/kreirajNalog")
    public ResponseEntity kreirajNalog(@Valid @RequestBody PodaciZaNalogDTO podaciZaNalogDTO, BindingResult bindingResult) {
         if (bindingResult.hasErrors())
            throw new BadRequestException();

        return new ResponseEntity<>(nalogZaPrenosService.kreirajNalog(podaciZaNalogDTO), HttpStatus.OK);
    }

    @Transactional
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody NalogZaPrenosDTO nalogZaPrenosDTO, BindingResult bindingResult) {
         if (bindingResult.hasErrors())
            throw new BadRequestException();

        return new ResponseEntity<>(nalogZaPrenosService.create(nalogZaPrenosDTO), HttpStatus.OK);
    }
}

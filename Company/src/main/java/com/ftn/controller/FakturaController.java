package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.model.dto.FakturaDTO;
import com.ftn.model.environment.EnvironmentProperties;
import com.ftn.service.FakturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * Created by JELENA on 19.6.2017.
 */
@RestController
@RequestMapping("/api/fakture")
public class FakturaController {

    private final FakturaService fakturaService;

    @Autowired
    private EnvironmentProperties environmentProperties;

    @Autowired
    public FakturaController(FakturaService fakturaService) {
        this.fakturaService = fakturaService;
    }

    @Transactional
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(fakturaService.read(), HttpStatus.OK);
    }

    @Transactional
    @GetMapping(value = "/firmaDobavljac/{pib}")
    public ResponseEntity readDobavljac(@PathVariable String pib) {
        return new ResponseEntity<>(fakturaService.readDobavljac(pib), HttpStatus.OK);
    }

    @Transactional
    @GetMapping(value = "/firmaKupac/{pib}")
    public ResponseEntity readKupac(@PathVariable String pib) {
        return new ResponseEntity<>(fakturaService.readKupac(pib), HttpStatus.OK);
    }

    @Transactional
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody FakturaDTO fakturaDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new BadRequestException();
        return new ResponseEntity<>(fakturaService.create(fakturaDTO), HttpStatus.OK);
    }

    @Transactional
    @PatchMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody FakturaDTO fakturaDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new BadRequestException();
        return new ResponseEntity<>(fakturaService.update(id, fakturaDTO), HttpStatus.OK);
    }
}

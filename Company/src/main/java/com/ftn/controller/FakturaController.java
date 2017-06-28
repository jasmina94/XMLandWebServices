package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.exception.BadRequestException;
import com.ftn.model.dto.FakturaDTO;
import com.ftn.model.environment.EnvironmentProperties;
import com.ftn.service.FakturaService;
import com.ftn.service.PDFGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private PDFGeneratorService pdfGeneratorService;


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
    @GetMapping(value = "/{id}")
    public ResponseEntity readFaktura(@PathVariable Long id) {
        return new ResponseEntity<>(fakturaService.readFaktura(id), HttpStatus.OK);
    }

    @Transactional
    @GetMapping(value = "/firmaDobavljac")
    public ResponseEntity readDobavljac() {
        return new ResponseEntity<>(fakturaService.readDobavljac(), HttpStatus.OK);
    }

    @Transactional
    @GetMapping(value = "/firmaKupac")
    public ResponseEntity readKupac() {
        return new ResponseEntity<>(fakturaService.readKupac(), HttpStatus.OK);
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

    @Transactional
    @PostMapping(value = "/generisiPdf")
    public ResponseEntity generisiPdf(@Valid @RequestBody FakturaDTO fakturaDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new BadRequestException();
        return new ResponseEntity<>(pdfGeneratorService.generisiFakturaPDF(fakturaDTO.construct()), HttpStatus.OK);
    }
}

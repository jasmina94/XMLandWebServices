package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.exception.BadRequestException;
import com.ftn.model.Faktura;
import com.ftn.model.dto.FakturaDTO;
import com.ftn.service.FakturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by JELENA on 19.6.2017.
 */
@RestController
@RequestMapping("/api/fakture")
public class FakturaController {

    private final FakturaService fakturaService;

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
    @GetMapping(value = "/firma/{pib}")
    public ResponseEntity read(@PathVariable String pib) {
        return new ResponseEntity<>(fakturaService.read(pib), HttpStatus.OK);
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

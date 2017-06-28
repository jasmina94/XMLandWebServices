package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.service.RobaUslugaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

/**
 * Created by JELENA on 22.6.2017.
 */
@RestController
@RequestMapping("/api/robeUsluge")
public class RobaUslugaController {

    private final RobaUslugaService robaUslugaService;

    @Autowired
    public RobaUslugaController(RobaUslugaService robaUslugaService) {
        this.robaUslugaService = robaUslugaService;
    }

    @Transactional
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(robaUslugaService.read(), HttpStatus.OK);
    }
}

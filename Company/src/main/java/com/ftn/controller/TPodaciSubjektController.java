package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.service.TPodaciSubjektService;
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
 * Created by JELENA on 20.6.2017.
 */
@RestController
@RequestMapping("/api/tPodaciSubjekti")
public class TPodaciSubjektController {

    private final TPodaciSubjektService tPodaciSubjektService;

    @Autowired
    public TPodaciSubjektController(TPodaciSubjektService tPodaciSubjektService) {
        this.tPodaciSubjektService = tPodaciSubjektService;
    }

    @Transactional
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(tPodaciSubjektService.read(), HttpStatus.OK);
    }

    @Transactional
    @GetMapping(value = "/poslovniPartneri/{id}")
    public ResponseEntity readPoslovniPartneri(@PathVariable Long id) {
        return new ResponseEntity<>(tPodaciSubjektService.readPoslovniPartneri(id), HttpStatus.OK);
    }
}

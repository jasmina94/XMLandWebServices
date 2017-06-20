package com.ftn.controller;

import com.ftn.service.TPodaciSubjektService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}

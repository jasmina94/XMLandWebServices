package com.ftn.controller;

import com.ftn.repository.DnevnoStanjeRacunaDao;
import com.ftn.service.DnevnoStanjeRacunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

/**
 * Created by JELENA on 27.6.2017.
 */
@RestController
@RequestMapping("/api/izvodi")
public class DnevnoStanjeRacunaController {

    private final DnevnoStanjeRacunaService dnevnoStanjeRacunaService;

    @Autowired
    public DnevnoStanjeRacunaController(DnevnoStanjeRacunaService dnevnoStanjeRacunaService) {
        this.dnevnoStanjeRacunaService = dnevnoStanjeRacunaService;
    }

    @Transactional
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(dnevnoStanjeRacunaService.read(), HttpStatus.OK);
    }
}

package com.ftn.controller;

import com.ftn.repository.DnevnoStanjeRacunaDao;
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

    private final DnevnoStanjeRacunaDao dnevnoStanjeRacunaDao;

    @Autowired
    public DnevnoStanjeRacunaController(DnevnoStanjeRacunaDao dnevnoStanjeRacunaDao) {
        this.dnevnoStanjeRacunaDao = dnevnoStanjeRacunaDao;
    }

    @Transactional
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(dnevnoStanjeRacunaDao.findAll(), HttpStatus.OK);
    }
}

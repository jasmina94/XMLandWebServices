package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.database.AnalitikaIzvoda;
import com.ftn.model.database.DnevnoStanjeRacuna;
import com.ftn.model.dto.DnevnoStanjeRacunaDTO;
import com.ftn.model.dto.FakturaDTO;
import com.ftn.repository.DnevnoStanjeRacunaDao;
import com.ftn.service.DnevnoStanjeRacunaService;
import com.ftn.service.PDFGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by JELENA on 27.6.2017.
 */
@RestController
@RequestMapping("/api/izvodi")
public class DnevnoStanjeRacunaController {

    private final DnevnoStanjeRacunaService dnevnoStanjeRacunaService;

    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    @Autowired
    private DnevnoStanjeRacunaDao dnevnoStanjeRacunaDao;

    @Autowired
    public DnevnoStanjeRacunaController(DnevnoStanjeRacunaService dnevnoStanjeRacunaService) {
        this.dnevnoStanjeRacunaService = dnevnoStanjeRacunaService;
    }

    @Transactional
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(dnevnoStanjeRacunaService.read(), HttpStatus.OK);
    }

    @Transactional
    @GetMapping(value = "/generisiPdf/{id}")
    public ResponseEntity generisiPdf(@PathVariable long id) {

        final DnevnoStanjeRacuna dnevnoStanjeRacuna = dnevnoStanjeRacunaDao.findById(id).orElseThrow(NotFoundException::new);
        ArrayList<AnalitikaIzvoda> analitikaIzvodas = new ArrayList<>(dnevnoStanjeRacuna.getAnalitikeIzvoda());
        pdfGeneratorService.generisiIzvodPDF(dnevnoStanjeRacuna, analitikaIzvodas);

        File file = new File("src/main/resources/izvod.pdf");

        return new ResponseEntity<>(file, HttpStatus.OK);
    }
}

package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.model.Faktura;
import com.ftn.model.NalogZaPrenos;
import com.ftn.model.TPodaciOPrenosu;
import com.ftn.model.TPrenosUcesnik;
import com.ftn.model.dto.FakturaDTO;
import com.ftn.model.dto.NalogZaPrenosDTO;
import com.ftn.model.dto.PodaciZaNalogDTO;
import com.ftn.service.NalogZaPrenosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import javax.print.attribute.standard.MediaTray;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Date;

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
    @PostMapping(value = "/kreirajNalog")
    public NalogZaPrenos kreirajNalog(@Valid @RequestBody PodaciZaNalogDTO podaciZaNalogDTO, BindingResult bindingResult) {
        System.out.print("Ovde sam kreiraj nalog kontrloer");
        System.out.print(podaciZaNalogDTO);
        System.out.print(podaciZaNalogDTO.getFaktura());

         if (bindingResult.hasErrors())
            throw new BadRequestException();

        NalogZaPrenos nalogZaPrenos = new NalogZaPrenos();
        TPodaciOPrenosu podaciOPrenosu = new TPodaciOPrenosu();
        TPrenosUcesnik duznikUPrenosu = new TPrenosUcesnik();
        TPrenosUcesnik poverilacUPrenosu= new TPrenosUcesnik();

      /*  nalogZaPrenos.setIdPoruke(faktura.getIdPoruke());
        nalogZaPrenos.setDuznik(faktura.getPodaciOKupcu().getNaziv());
        nalogZaPrenos.setPoverilac(faktura.getPodaciODobavljacu().getNaziv());
        nalogZaPrenos.setSvrhaPlacanja("Placanje po fakturi " + faktura.getBrojRacuna());
        nalogZaPrenos.setDatumNaloga(faktura.getDatumRacuna());
        nalogZaPrenos.setDatumValute(new Date());
        podaciOPrenosu.setIznos(faktura.getIznosZaUplatu());
        podaciOPrenosu.setOznakaValute(faktura.getOznakaValute());


        //TODO: Popuni ostala polja
        //poverilacUPrenosu.setRacunUcesnika(faktura.getUplataNaRacun());

        // podaciOPrenosu.setPoverilacUPrenosu();
        //podaciOPrenosu.setDuznikUPrenosu();
        return nalogZaPrenos;
*/
      return null;
        //TODO: sacuvaj u bazu
        // return new ResponseEntity<>(nalogZaPrenosService.create(nalogZaPrenos), HttpStatus.OK);
    }

    @Transactional
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody NalogZaPrenosDTO nalogZaPrenosDTO, BindingResult bindingResult) {
       System.out.print("Ovde sam kreate");
        // if (bindingResult.hasErrors())
        //    throw new BadRequestException();

        return null;
       // return new ResponseEntity<>(nalogZaPrenosService.create(nalogZaPrenos), HttpStatus.OK);
    }
}

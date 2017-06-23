package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.model.*;
import com.ftn.model.dto.FakturaDTO;
import com.ftn.model.dto.NalogZaPrenosDTO;
import com.ftn.model.dto.PodaciZaNalogDTO;
import com.ftn.repository.FakturaDao;
import com.ftn.repository.NalogZaPrenosDao;
import com.ftn.service.FakturaService;
import com.ftn.service.NalogZaPrenosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Olivera on 22.6.2017..
 */
@Service
public class NalogZaPrenosServiceImplementation implements NalogZaPrenosService {

    private final NalogZaPrenosDao nalogZaPrenosDao;
    private final FakturaDao fakturaDao;

    @Autowired
    public NalogZaPrenosServiceImplementation(NalogZaPrenosDao nalogZaPrenosDao, FakturaDao fakturaDao) {
        this.nalogZaPrenosDao = nalogZaPrenosDao;
        this.fakturaDao = fakturaDao;
    }

    @Override
    public List<NalogZaPrenosDTO> read() {
        return nalogZaPrenosDao.findAll().stream().map(NalogZaPrenosDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<NalogZaPrenosDTO> readPoverilac(String naziv) {
        List<NalogZaPrenos> nalozi = new ArrayList<>();
        for(NalogZaPrenos nalog : nalogZaPrenosDao.findAll()) {
            if(nalog.getPoverilac().equals(naziv)) {
                nalozi.add(nalog);
            }
        }
        return nalozi.stream().map(NalogZaPrenosDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<NalogZaPrenosDTO> readDuznik(String naziv) {
        List<NalogZaPrenos> nalozi = new ArrayList<>();
        System.out.println(nalogZaPrenosDao.findAll());
        System.out.println(naziv);
        for(NalogZaPrenos nalog : nalogZaPrenosDao.findAll()) {
            System.out.println(nalog.getDuznik());
            if(nalog.getDuznik().equals(naziv)) {
                System.out.println(nalog.getDuznik());
                nalozi.add(nalog);
            }
        }
        return nalozi.stream().map(NalogZaPrenosDTO::new).collect(Collectors.toList());
    }

    @Override
    public NalogZaPrenosDTO create(NalogZaPrenosDTO nalogZaPrenosDTO) {
        if (nalogZaPrenosDao.findById(nalogZaPrenosDTO.getId()).isPresent())
            throw new BadRequestException();

        final NalogZaPrenos nalogZaPrenos = nalogZaPrenosDTO.construct();
        nalogZaPrenosDao.save(nalogZaPrenos);
        return new NalogZaPrenosDTO(nalogZaPrenos);
    }

    @Override
    public NalogZaPrenosDTO kreirajNalog(PodaciZaNalogDTO podaciZaNalogDTO) {
        NalogZaPrenos nalogZaPrenos = new NalogZaPrenos();
        TPodaciOPrenosu podaciOPrenosu = new TPodaciOPrenosu();
        TPrenosUcesnik duznikUPrenosu = new TPrenosUcesnik();
        TPrenosUcesnik poverilacUPrenosu= new TPrenosUcesnik();

        poverilacUPrenosu.setRacunUcesnika(podaciZaNalogDTO.getFaktura().getUplataNaRacun());
        poverilacUPrenosu.setPozivNaBroj(podaciZaNalogDTO.getPozivNaBrojOdobrenja());
        poverilacUPrenosu.setModelPrenosa(podaciZaNalogDTO.getModelOdobrenja());
        duznikUPrenosu.setRacunUcesnika(podaciZaNalogDTO.getRacunDuznika());
        duznikUPrenosu.setPozivNaBroj(podaciZaNalogDTO.getPozivNaBrojZaduzenja());
        duznikUPrenosu.setModelPrenosa(podaciZaNalogDTO.getModelZaduzenja());

        nalogZaPrenos.setIdPoruke(podaciZaNalogDTO.getFaktura().getIdPoruke());
        System.out.println(podaciZaNalogDTO.getFaktura().getPodaciOKupcu().getNaziv());
        nalogZaPrenos.setDuznik(podaciZaNalogDTO.getFaktura().getPodaciOKupcu().getNaziv());
        nalogZaPrenos.setPoverilac(podaciZaNalogDTO.getFaktura().getPodaciODobavljacu().getNaziv());
        nalogZaPrenos.setSvrhaPlacanja("Placanje po fakturi " + podaciZaNalogDTO.getFaktura().getBrojRacuna());
        nalogZaPrenos.setDatumNaloga(podaciZaNalogDTO.getFaktura().getDatumRacuna());
        nalogZaPrenos.setDatumValute(new Date());
        nalogZaPrenos.setHitno(podaciZaNalogDTO.isHitno());
        podaciOPrenosu.setIznos(podaciZaNalogDTO.getFaktura().getIznosZaUplatu());
        podaciOPrenosu.setOznakaValute(podaciZaNalogDTO.getFaktura().getOznakaValute());
        podaciOPrenosu.setPoverilacUPrenosu(poverilacUPrenosu);
        podaciOPrenosu.setDuznikUPrenosu(duznikUPrenosu);
        nalogZaPrenos.setPodaciOPrenosu(podaciOPrenosu);

        NalogZaPrenosDTO kreiranNalogDTO = create(new NalogZaPrenosDTO(nalogZaPrenos));
        podaciZaNalogDTO.getFaktura().setKreiranNalog(true);
        fakturaDao.save(podaciZaNalogDTO.getFaktura());
        return kreiranNalogDTO;
    }
}

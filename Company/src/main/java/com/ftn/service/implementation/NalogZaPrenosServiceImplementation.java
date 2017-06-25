package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.model.*;
import com.ftn.model.dto.NalogZaPrenosDTO;
import com.ftn.model.dto.PodaciZaNalogDTO;

import com.ftn.model.generated.faktura.Faktura;
import com.ftn.model.generated.nalog_za_prenos.NalogZaPrenos;
import com.ftn.model.generated.tipovi.TPodaciOPrenosu;
import com.ftn.model.generated.tipovi.TPrenosUcesnik;
import com.ftn.repository.FakturaDao;
import com.ftn.repository.NalogZaPrenosDao;
import com.ftn.service.NalogZaPrenosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Olivera on 22.6.2017..
 */

@Service
public class NalogZaPrenosServiceImplementation implements NalogZaPrenosService {
//    @Override
//    public List<NalogZaPrenosDTO> read() {
//        return null;
//    }
//
//    @Override
//    public List<NalogZaPrenosDTO> readPoverilac(String naziv) {
//        return null;
//    }
//
//    @Override
//    public List<NalogZaPrenosDTO> readDuznik(String naziv) {
//        return null;
//    }
//
//    @Override
//    public NalogZaPrenosDTO create(NalogZaPrenosDTO nalogZaPrenosDTO) {
//        return null;
//    }
//
//    @Override
//    public NalogZaPrenosDTO kreirajNalog(PodaciZaNalogDTO podaciZaNalogDTO) {
//        return null;
//    }


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
        for(NalogZaPrenos nalog : nalogZaPrenosDao.findAll()) {
            if(nalog.getDuznik().equals(naziv)) {
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
        File file = new File("src/main/resources/xmlFiles/faktura.xml");

        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Faktura.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(podaciZaNalogDTO.getFaktura(), file);
        }catch (Exception e) {
            System.out.println("GRESKA: " + e);
            return null;
        }

        if(!validateXMLSchema("src/main/resources/schemas/faktura_schema.xsd", "src/main/resources/xmlFiles/faktura.xml")) {
            return null;
        }

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


    public static boolean validateXMLSchema(String xsdPath, String xmlPath){
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: "+ e.getMessage());
            return false;
        }
        return true;
    }
}

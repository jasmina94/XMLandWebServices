package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.dto.FakturaDTO;
import com.ftn.model.dto.TStavkaFakturaDTO;
import com.ftn.model.environment.EnvironmentProperties;
import com.ftn.model.generated.faktura.Faktura;
import com.ftn.model.generated.faktura.GetFakturaRequest;
import com.ftn.model.generated.tipovi.TStavkaFaktura;
import com.ftn.repository.FakturaDao;
import com.ftn.repository.TPodaciSubjekatDao;
import com.ftn.service.FakturaService;
import com.ftn.service.PDFGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by JELENA on 19.6.2017.
 */

@Service
public class FakturaServiceImplementation implements FakturaService {


    private final FakturaDao fakturaDao;
    private final TPodaciSubjekatDao tPodaciSubjekatDao;
    private final EnvironmentProperties environmentProperties;

    @Autowired
    public FakturaServiceImplementation(FakturaDao fakturaDao, TPodaciSubjekatDao tPodaciSubjekatDao, EnvironmentProperties environmentProperties) {
        this.fakturaDao = fakturaDao;
        this.tPodaciSubjekatDao = tPodaciSubjekatDao;
        this.environmentProperties = environmentProperties;
    }

    @Override
    public List<FakturaDTO> read() {
        return fakturaDao.findAll().stream().map(FakturaDTO::new).collect(Collectors.toList());
    }

    @Override
    public FakturaDTO readFaktura(Long id) {
        return new FakturaDTO(fakturaDao.findById(id).get());
    }

    @Override
    public List<FakturaDTO> readDobavljac() {
        List<Faktura> faktureFirme = new ArrayList<>();
        for(Faktura faktura : fakturaDao.findAll()) {
            if(faktura.getPodaciODobavljacu().getPib().equals(environmentProperties.getPib())) {
                faktureFirme.add(faktura);
            }
        }
        return faktureFirme.stream().map(FakturaDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<FakturaDTO> readKupac() {
        List<Faktura> faktureFirme = new ArrayList<>();
        for(Faktura faktura : fakturaDao.findAll()) {
            if(faktura.getPodaciOKupcu().getPib().equals(environmentProperties.getPib())) {
                faktureFirme.add(faktura);
            }
        }
        return faktureFirme.stream().map(FakturaDTO::new).collect(Collectors.toList());
    }

    @Override
    public FakturaDTO create(FakturaDTO fakturaDTO) {
        fakturaDTO.setId(0);

        fakturaDTO.setUplataNaRacun(fakturaDTO.getPodaciODobavljacu().getRacunFirme());
        String UUIDString = UUID.randomUUID().toString();
        while (UUIDString.length() > 50)
            UUIDString = UUID.randomUUID().toString();

        fakturaDTO.setIdPoruke(UUIDString);

        System.out.println("uuid: " + fakturaDTO.getIdPoruke());
        final Faktura faktura = fakturaDTO.construct();

        if (!tPodaciSubjekatDao.findById(fakturaDTO.getPodaciOKupcu().getId()).isPresent() || !tPodaciSubjekatDao.findById(fakturaDTO.getPodaciODobavljacu().getId()).isPresent())
            throw new BadRequestException();

        faktura.setPodaciOKupcu((tPodaciSubjekatDao.findById(fakturaDTO.getPodaciOKupcu().getId()).get()));
        faktura.setPodaciODobavljacu((tPodaciSubjekatDao.findById(fakturaDTO.getPodaciODobavljacu().getId()).get()));

        ArrayList<TStavkaFaktura> stavke = new ArrayList<>();
        for (TStavkaFakturaDTO stavkaDTO: fakturaDTO.getStavkaFakture())
            stavke.add(stavkaDTO.construct());
        faktura.setStavkaFakture(stavke);

        fakturaDao.save(faktura);
        return new FakturaDTO(faktura);
    }

    @Override
    public FakturaDTO update(Long id, FakturaDTO fakturaDTO) {

        final Faktura faktura = fakturaDao.findById(id).orElseThrow(NotFoundException::new);

        if (!faktura.isPoslato() && fakturaDTO.isPoslato()) {
            fakturaDTO.setDatumValute(new Date());

            File file = new File("src/main/resources/faktura.xml");

            JAXBContext jaxbContext;
            try {
                jaxbContext = JAXBContext.newInstance(GetFakturaRequest.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                final GetFakturaRequest getFakturaRequest = new GetFakturaRequest();
                getFakturaRequest.setFaktura(fakturaDTO.construct());
                jaxbMarshaller.marshal(getFakturaRequest, file);
            }catch (Exception e) {
                System.out.println("GRESKA: " + e);
                return null;
            }

            if(!validateXMLSchema("src/main/resources/faktura.xsd", "src/main/resources/faktura.xml")) {
                return null;
            }

            final RestTemplate restTemplate = new RestTemplate();
            final String firmaUrl = tPodaciSubjekatDao.findByPib(faktura.getPodaciOKupcu().getPib()).get().getCompanyUrl();
            final FakturaDTO fakturaResponse = restTemplate.postForObject(firmaUrl + "api/fakture", fakturaDTO, FakturaDTO.class);
        } else {
            double vrednostRobe = 0.0;
            double vrednostUsluga = 0.0;
            double vrednostRobaIUsluga = 0.0;
            double ukupanRabat = 0.0;
            double ukupanPorez = 0.0;


            for (TStavkaFakturaDTO stavka: fakturaDTO.getStavkaFakture()) {
                if (stavka.isRoba())
                    vrednostRobe += stavka.getVrednost().doubleValue();
                else
                    vrednostUsluga += stavka.getVrednost().doubleValue();

                vrednostRobaIUsluga += stavka.getVrednost().doubleValue();
                ukupanRabat += stavka.getIznosRabata().doubleValue();
                ukupanPorez += stavka.getUkupanPorez().doubleValue();
            }

            fakturaDTO.setVrednostRobe(BigDecimal.valueOf(vrednostRobe));
            fakturaDTO.setVrednostUsluga(BigDecimal.valueOf(vrednostUsluga));
            fakturaDTO.setUkupnoRobaIUsluga(BigDecimal.valueOf(vrednostRobaIUsluga));
            fakturaDTO.setUkupanRabat(BigDecimal.valueOf(ukupanRabat));
            fakturaDTO.setUkupanPorez(BigDecimal.valueOf(ukupanPorez));
            fakturaDTO.setIznosZaUplatu(BigDecimal.valueOf(vrednostRobaIUsluga + ukupanPorez - ukupanRabat));

        }

        faktura.merge(fakturaDTO);
        fakturaDao.save(faktura);
        return new FakturaDTO(faktura);
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

package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.model.dto.NalogZaPrenosDTO;
import com.ftn.model.dto.PodaciZaNalogDTO;

import com.ftn.model.environment.EnvironmentProperties;
import com.ftn.model.generated.faktura.Faktura;
import com.ftn.model.generated.faktura.GetFakturaRequest;
import com.ftn.model.generated.nalog_za_prenos.GetNalogZaPrenosRequest;
import com.ftn.model.generated.nalog_za_prenos.GetNalogZaPrenosResponse;
import com.ftn.model.generated.nalog_za_prenos.NalogZaPrenos;
import com.ftn.model.generated.tipovi.TPodaciOPrenosu;
import com.ftn.model.generated.tipovi.TPrenosUcesnik;
import com.ftn.repository.FakturaDao;
import com.ftn.repository.NalogZaPrenosDao;
import com.ftn.service.NalogZaPrenosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
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
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Olivera on 22.6.2017..
 */

@Service
public class NalogZaPrenosServiceImplementation extends WebServiceGatewaySupport implements NalogZaPrenosService {

    private final NalogZaPrenosDao nalogZaPrenosDao;

    private final FakturaDao fakturaDao;

    private final EnvironmentProperties environmentProperties;

    @Autowired
    public NalogZaPrenosServiceImplementation(NalogZaPrenosDao nalogZaPrenosDao, FakturaDao fakturaDao, EnvironmentProperties environmentProperties) {
        this.nalogZaPrenosDao = nalogZaPrenosDao;
        this.fakturaDao = fakturaDao;
        this.environmentProperties = environmentProperties;
    }

    @Override
    public List<NalogZaPrenosDTO> read() {
        return nalogZaPrenosDao.findAll().stream().map(NalogZaPrenosDTO::new).collect(Collectors.toList());
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
        File file = new File("src/main/resources/faktura.xml");

        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(GetFakturaRequest.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            final GetFakturaRequest getFakturaRequest = new GetFakturaRequest();
            getFakturaRequest.setFaktura(podaciZaNalogDTO.getFaktura());
            jaxbMarshaller.marshal(getFakturaRequest, file);
        }catch (Exception e) {
            System.out.println("GRESKA: " + e);
            return null;
        }

        if(!validateXMLSchema("src/main/resources/faktura.xsd", "src/main/resources/faktura.xml")) {
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

        //TODO: Testiraj ovo
        String UUIDString = UUID.randomUUID().toString();
        while (UUIDString.length() > 50)
            UUIDString = UUID.randomUUID().toString();

        nalogZaPrenos.setIdPoruke(UUIDString);
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

        if(!send(nalogZaPrenos)) {
            return null;
        }
        NalogZaPrenosDTO kreiranNalogDTO = create(new NalogZaPrenosDTO(nalogZaPrenos));
        podaciZaNalogDTO.getFaktura().setKreiranNalog(true);
        fakturaDao.save(podaciZaNalogDTO.getFaktura());
        return kreiranNalogDTO;
    }

    private boolean send(NalogZaPrenos nalogZaPrenos) {

        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(GetNalogZaPrenosRequest.class, GetNalogZaPrenosResponse.class);
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);

        final GetNalogZaPrenosRequest getNalogZaPrenosRequest = new GetNalogZaPrenosRequest();
        getNalogZaPrenosRequest.setNalogZaPrenos(nalogZaPrenos);
        try{
            final GetNalogZaPrenosResponse response = (GetNalogZaPrenosResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(environmentProperties.getBankUrl(), getNalogZaPrenosRequest);
        }catch (Exception e) {
            return false;
        }
        return true;
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

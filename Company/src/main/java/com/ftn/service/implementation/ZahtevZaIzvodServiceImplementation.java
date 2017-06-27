package com.ftn.service.implementation;

import com.ftn.model.database.DnevnoStanjeRacuna;
import com.ftn.model.environment.EnvironmentProperties;
import com.ftn.model.generated.zahtevzaizvod.GetZahtevZaIzvodRequest;
import com.ftn.model.generated.zahtevzaizvod.GetZahtevZaIzvodResponse;
import com.ftn.model.generated.zahtevzaizvod.ZahtevZaIzvod;
import com.ftn.repository.DnevnoStanjeRacunaDao;
import com.ftn.service.ZahtevZaIzvodService;
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
import java.util.Optional;

/**
 * Created by Olivera on 26.6.2017..
 */
@Service
public class ZahtevZaIzvodServiceImplementation  extends WebServiceGatewaySupport implements ZahtevZaIzvodService {

    private final EnvironmentProperties environmentProperties;


    @Autowired
    public ZahtevZaIzvodServiceImplementation(EnvironmentProperties environmentProperties) {
        this.environmentProperties = environmentProperties;
    }

    @Override
    public boolean posaljiZahtev(ZahtevZaIzvod zahtevZaIzvod) {
        File file = new File("src/main/resources/zahtevZaIzvod.xml");
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(GetZahtevZaIzvodRequest.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            final GetZahtevZaIzvodRequest getZahtevZaIzvodRequest = new GetZahtevZaIzvodRequest();
            getZahtevZaIzvodRequest.setZahtevZaIzvod(zahtevZaIzvod);
            jaxbMarshaller.marshal(getZahtevZaIzvodRequest, file);
        }catch (Exception e) {
            System.out.println("GRESKA: " + e);
            return false;
        }

        if(!validateXMLSchema("src/main/resources/zahtev_za_izvod.xsd", "src/main/resources/zahtevZaIzvod.xml")) {
            return false;
        }

        return send(zahtevZaIzvod);
    }

    private boolean send(ZahtevZaIzvod zahtevZaIzvod) {

        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(GetZahtevZaIzvodRequest.class, GetZahtevZaIzvodResponse.class);
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);

        final GetZahtevZaIzvodRequest getZahtevZaIzvodRequest = new GetZahtevZaIzvodRequest();
        getZahtevZaIzvodRequest.setZahtevZaIzvod(zahtevZaIzvod);
        try{
            final GetZahtevZaIzvodResponse response = (GetZahtevZaIzvodResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(environmentProperties.getBankUrl(), getZahtevZaIzvodRequest);
        } catch (Exception e) {
            //TODO: Dodaj handlovanje greskama, zbog cega tacno nije proslo ----- nekako iscitati iz exceptiona
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

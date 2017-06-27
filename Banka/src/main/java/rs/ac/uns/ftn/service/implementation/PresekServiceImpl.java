package rs.ac.uns.ftn.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import rs.ac.uns.ftn.model.dto.mt103.GetMt103Request;
import rs.ac.uns.ftn.model.dto.mt103.GetMt103Response;
import rs.ac.uns.ftn.model.dto.presek.GetPresekRequest;
import rs.ac.uns.ftn.model.dto.presek.GetPresekResponse;
import rs.ac.uns.ftn.model.dto.presek.Presek;
import rs.ac.uns.ftn.model.environment.EnvironmentProperties;
import rs.ac.uns.ftn.service.PresekService;

/**
 * Created by Jasmina on 26/06/2017.
 */
@Service
public class PresekServiceImpl extends WebServiceGatewaySupport implements PresekService {

    @Autowired
    private EnvironmentProperties environmentProperties;

    @Override
    public void send(Presek presek) {
        String url = "";
        String brojRacna = presek.getZaglavljePreseka().getBrojRacuna();
        if(brojRacna.startsWith("265")){
            url = environmentProperties.getFirmaBUrl();
        }else {
            url = environmentProperties.getFirmaAUrl();
        }
        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(GetPresekRequest.class, GetPresekResponse.class);
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);

        final GetPresekRequest getPresekRequest = new GetPresekRequest();
        getPresekRequest.setPresek(presek);

        final GetPresekResponse response = (GetPresekResponse) getWebServiceTemplate().marshalSendAndReceive(url, getPresekRequest);
    }
}

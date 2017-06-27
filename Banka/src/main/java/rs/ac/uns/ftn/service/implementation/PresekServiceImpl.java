package rs.ac.uns.ftn.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import rs.ac.uns.ftn.exception.ServiceFaultException;
import rs.ac.uns.ftn.model.database.Firma;
import rs.ac.uns.ftn.model.dto.error.ServiceFault;
import rs.ac.uns.ftn.model.dto.mt103.GetMt103Request;
import rs.ac.uns.ftn.model.dto.mt103.GetMt103Response;
import rs.ac.uns.ftn.model.dto.presek.GetPresekRequest;
import rs.ac.uns.ftn.model.dto.presek.GetPresekResponse;
import rs.ac.uns.ftn.model.dto.presek.Presek;
import rs.ac.uns.ftn.model.environment.EnvironmentProperties;
import rs.ac.uns.ftn.repository.FirmaRepository;
import rs.ac.uns.ftn.service.PresekService;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Created by Jasmina on 26/06/2017.
 */
@Service
public class PresekServiceImpl extends WebServiceGatewaySupport implements PresekService {

    @Autowired
    private FirmaRepository firmaRepository;

    @Override
    public void send(Presek presek) {
        String url = "";
        String brojRacna = presek.getZaglavljePreseka().getBrojRacuna();
        Optional<Firma> firma = firmaRepository.findByBrojRacuna(brojRacna);
        if(firma.isPresent()){
            url = firma.get().getUrl();
        }else{
            throw new ServiceFaultException("Nije pronadjen.", new ServiceFault("404", "Nije pronadjena firma sa racunom u banci kojoj se vraca izvod!"));
        }

        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(GetPresekRequest.class, GetPresekResponse.class);
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);

        final GetPresekRequest getPresekRequest = new GetPresekRequest();
        getPresekRequest.setPresek(presek);

        try{
            final GetPresekResponse response = (GetPresekResponse) getWebServiceTemplate().marshalSendAndReceive(url, getPresekRequest);
        }catch (RuntimeException e){
            throw new ServiceFaultException("Pogresan odgovor.", new ServiceFault("500", "Nemoguce slanje preseka firmi."));
        }
    }
}

package rs.ac.uns.ftn.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import rs.ac.uns.ftn.model.dto.nalog_za_prenos.GetNalogZaPrenosRequest;
import rs.ac.uns.ftn.model.dto.nalog_za_prenos.GetNalogZaPrenosResponse;
import rs.ac.uns.ftn.model.dto.nalog_za_prenos.NalogZaPrenos;
import rs.ac.uns.ftn.model.environment.EnvironmentProperties;
import rs.ac.uns.ftn.service.PlacanjeService;

/**
 * Created by Jasmina on 24/06/2017.
 */
@Endpoint
public class NalogEndpoint {

    public static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/nalog_za_prenos";

    @Autowired
    private PlacanjeService servisPlacanje;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getNalogZaPrenosRequest")
    @ResponsePayload
    public GetNalogZaPrenosResponse nalog(@RequestPayload GetNalogZaPrenosRequest request) {
        final GetNalogZaPrenosResponse response = new GetNalogZaPrenosResponse();
        NalogZaPrenos nalogZaPrenos = request.getNalogZaPrenos();
        servisPlacanje.process(nalogZaPrenos);
        response.setNalogZaPrenos("Ok");
        return response;
    }
}
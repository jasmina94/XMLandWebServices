package rs.ac.uns.ftn.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import rs.ac.uns.ftn.model.dto.zahtev_za_izvod.GetZahtevZaIzvodRequest;
import rs.ac.uns.ftn.model.dto.zahtev_za_izvod.GetZahtevZaIzvodResponse;
import rs.ac.uns.ftn.model.environment.EnvironmentProperties;
import rs.ac.uns.ftn.service.ZahtevService;

/**
 * Created by Jasmina on 24/06/2017.
 */
@Endpoint
public class ZahtevEndpoint {

    public static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/zahtevZaIzvod";

    @Autowired
    private EnvironmentProperties environmentProperties;

    @Autowired
    private ZahtevService zahtevService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getZahtevZaIzvodRequest")
    @ResponsePayload
    public GetZahtevZaIzvodResponse zahtev(@RequestPayload GetZahtevZaIzvodRequest request) {
        final GetZahtevZaIzvodResponse response = new GetZahtevZaIzvodResponse();
        zahtevService.process(request.getZahtevZaIzvod());
        response.setZahtevZaIzvod("Ok");
        return response;
    }
}

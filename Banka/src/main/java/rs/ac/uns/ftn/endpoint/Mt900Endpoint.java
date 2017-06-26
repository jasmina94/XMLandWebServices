package rs.ac.uns.ftn.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import rs.ac.uns.ftn.model.dto.mt900.GetMt900Request;
import rs.ac.uns.ftn.model.dto.mt900.GetMt900Response;
import rs.ac.uns.ftn.model.environment.EnvironmentProperties;
import rs.ac.uns.ftn.service.ClearingService;
import rs.ac.uns.ftn.service.RTGSService;

/**
 * Cre
 * ated by Jasmina on 24/06/2017.
 */
@Endpoint
public class Mt900Endpoint {
    private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/mt900";

    @Autowired
    private EnvironmentProperties environmentProperties;

    @Autowired
    private RTGSService rtgsService;

    @Autowired
    private ClearingService clearingService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMt900Request")
    @ResponsePayload
    public GetMt900Response mt900(@RequestPayload GetMt900Request request) {
        final GetMt900Response response = new GetMt900Response();
        //rtgsService.processMT900(request.getMt900());
        String pom = request.getMt900().getPodaciONalogu().getIdPorukeNaloga();
        System.out.println("vraca se mt900");
        System.out.println("stize " + pom);

        String ret = clearingService.processMT900(request.getMt900());
        System.out.println(ret);
        response.setMt900("Ok");
        return response;
    }
}

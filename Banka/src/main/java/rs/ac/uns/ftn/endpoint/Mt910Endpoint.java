package rs.ac.uns.ftn.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import rs.ac.uns.ftn.model.dto.mt910.GetMt910Request;
import rs.ac.uns.ftn.model.dto.mt910.GetMt910Response;
import rs.ac.uns.ftn.model.environment.EnvironmentProperties;
import rs.ac.uns.ftn.service.ClearingService;
import rs.ac.uns.ftn.service.RTGSService;

import javax.jws.Oneway;

/**
 * Created by Jasmina on 24/06/2017.
 */
@Endpoint
public class Mt910Endpoint {

    public static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/mt910";

    @Autowired
    private EnvironmentProperties environmentProperties;

    @Autowired
    private RTGSService rtgsService;

    @Autowired
    private ClearingService clearingService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMt910Request")
    @ResponsePayload
    public GetMt910Response mt910(@RequestPayload GetMt910Request request) {
        final GetMt910Response response = new GetMt910Response();
        if(request.getMt910().getPodaciONalogu().getIdPorukeNaloga().startsWith("Mt102")){
            clearingService.processMT910(request.getMt910());
            response.setMt910("Ok");
            return response;
        }else {
            rtgsService.processMT910(request.getMt910());
            response.setMt910("Ok");
            return response;
        }
    }
}

package rs.ac.uns.ftn.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import rs.ac.uns.ftn.model.dto.mt910.GetMt910Request;
import rs.ac.uns.ftn.model.dto.mt910.GetMt910Response;
import rs.ac.uns.ftn.model.environment.EnvironmentProperties;

/**
 * Created by Jasmina on 24/06/2017.
 */
@Endpoint
public class Mt910Endpoint {
    private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/mt910";

    @Autowired
    private EnvironmentProperties environmentProperties;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMt910Request")
    @ResponsePayload
    public GetMt910Response mt910(@RequestPayload GetMt910Request request) {
        final GetMt910Response response = new GetMt910Response();
        response.setMt910("Ok");
        return response;
    }
}

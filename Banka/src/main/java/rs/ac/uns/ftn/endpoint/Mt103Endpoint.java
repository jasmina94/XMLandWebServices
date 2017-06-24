package rs.ac.uns.ftn.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import rs.ac.uns.ftn.model.dto.mt103.GetMt103Request;
import rs.ac.uns.ftn.model.dto.mt103.GetMt103Response;
import rs.ac.uns.ftn.model.environment.EnvironmentProperties;

/**
 * Created by Jasmina on 24/06/2017.
 */
@Endpoint
public class Mt103Endpoint {
    private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/mt103";

    @Autowired
    private EnvironmentProperties environmentProperties;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMt103Request")
    @ResponsePayload
    public GetMt103Response mt103(@RequestPayload GetMt103Request request) {
        final GetMt103Response response = new GetMt103Response();
        response.setMt103("Ok");
        return response;
    }
}

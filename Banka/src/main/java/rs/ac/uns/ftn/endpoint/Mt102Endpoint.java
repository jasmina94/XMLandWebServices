package rs.ac.uns.ftn.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import rs.ac.uns.ftn.model.dto.mt102.GetMt102Request;
import rs.ac.uns.ftn.model.dto.mt102.GetMt102Response;
import rs.ac.uns.ftn.model.environment.EnvironmentProperties;

/**
 * Created by Jasmina on 24/06/2017.
 */
@Endpoint
public class Mt102Endpoint {

    private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/mt102";

    @Autowired
    private EnvironmentProperties environmentProperties;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMt102Request")
    @ResponsePayload
    public GetMt102Response mt103(@RequestPayload GetMt102Request request) {
        final GetMt102Response response = new GetMt102Response();
        response.setMt102("Ok");
        return response;
    }
}

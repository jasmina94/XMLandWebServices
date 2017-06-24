package rs.ac.uns.ftn.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import rs.ac.uns.ftn.model.dto.presek.GetPresekRequest;
import rs.ac.uns.ftn.model.dto.presek.GetPresekResponse;
import rs.ac.uns.ftn.model.environment.EnvironmentProperties;

/**
 * Created by Jasmina on 24/06/2017.
 */
@Endpoint
public class PresekEndpoint {
    private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/presek";

    @Autowired
    private EnvironmentProperties environmentProperties;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPresekRequest")
    @ResponsePayload
    public GetPresekResponse presek(@RequestPayload GetPresekRequest request) {
        final GetPresekResponse response = new GetPresekResponse();
        response.setPresek("Ok");
        return response;
    }
}

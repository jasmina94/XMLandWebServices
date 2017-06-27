package com.ftn.endpoint;

import com.ftn.model.environment.EnvironmentProperties;
import com.ftn.model.generated.presek.GetPresekRequest;
import com.ftn.model.generated.presek.GetPresekResponse;
import com.ftn.model.generated.presek.Presek;
import com.ftn.service.PresekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Created by Olivera on 26.6.2017..
 */
@Endpoint
public class PresekEndpoint {

    public static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/presek";

    @Autowired
    private EnvironmentProperties environmentProperties;

    @Autowired
    private PresekService presekService;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPresekRequest")
    @ResponsePayload
    public GetPresekResponse presek(@RequestPayload GetPresekRequest request) {
        final GetPresekResponse response = new GetPresekResponse();
        Presek presek = request.getPresek();
        presekService.process(presek);
        response.setPresek("Ok");
        return response;
    }
}

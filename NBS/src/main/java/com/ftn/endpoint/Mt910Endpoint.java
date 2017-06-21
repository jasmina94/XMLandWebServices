package com.ftn.endpoint;

import com.ftn.model.request.Mt910Request;
import com.ftn.model.response.Mt910Response;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Created by Alex on 6/21/17.
 */
@Endpoint
public class Mt910Endpoint {
    private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/mt910";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMt910Request")
    @ResponsePayload
    public Mt910Response mt910(@RequestPayload Mt910Request request) {
        final Mt910Response response = new Mt910Response();
        response.setMt910(request.getMt910());
        return response;
    }
}

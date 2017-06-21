package com.ftn.endpoint;

import com.ftn.model.request.Mt900Request;
import com.ftn.model.response.Mt900Response;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Created by Alex on 6/21/17.
 */
@Endpoint
public class Mt900Endpoint {

    private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/mt900";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMt900Request")
    @ResponsePayload
    public Mt900Response mt900(@RequestPayload Mt900Request request) {
        final Mt900Response response = new Mt900Response();
        response.setMt900(request.getMt900());
        return response;
    }
}

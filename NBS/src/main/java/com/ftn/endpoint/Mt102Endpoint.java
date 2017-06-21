package com.ftn.endpoint;

import com.ftn.model.request.Mt102Request;
import com.ftn.model.response.Mt102Response;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Created by Alex on 6/21/17.
 */
@Endpoint
public class Mt102Endpoint {

    private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/mt102";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMt102Request")
    @ResponsePayload
    public Mt102Response mt103(@RequestPayload Mt102Request request) {
        final Mt102Response response = new Mt102Response();
        response.setMt102(request.getMt102());
        return response;
    }
}

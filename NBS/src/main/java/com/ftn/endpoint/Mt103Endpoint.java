package com.ftn.endpoint;

import com.ftn.model.request.Mt103Request;
import com.ftn.model.request.Mt103Response;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Created by Alex on 6/21/17.
 */
@Endpoint
public class Mt103Endpoint {

    private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/mt103";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMt103Request")
    @ResponsePayload
    public Mt103Response mt103(@RequestPayload Mt103Request request) {
        final Mt103Response response = new Mt103Response();
        response.setMt1033(request.getMt103());
        return response;
    }
}
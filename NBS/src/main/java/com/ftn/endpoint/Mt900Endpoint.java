package com.ftn.endpoint;

import com.ftn.model.dto.mt900.GetMt900Request;
import com.ftn.model.dto.mt900.GetMt900Response;
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
    public GetMt900Response mt900(@RequestPayload GetMt900Request request) {
        final GetMt900Response response = new GetMt900Response();
        response.setMt900("Ok");
        return response;
    }
}

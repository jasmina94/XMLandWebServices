package com.ftn.endpoint;

import com.ftn.model.dto.mt102.GetMt102Request;
import com.ftn.model.dto.mt102.GetMt102Response;
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
    public GetMt102Response mt103(@RequestPayload GetMt102Request request) {
        final GetMt102Response response = new GetMt102Response();
        response.setMt102("Ok");
        return response;
    }
}

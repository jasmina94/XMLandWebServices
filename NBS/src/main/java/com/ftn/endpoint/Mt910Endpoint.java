package com.ftn.endpoint;

import com.ftn.model.dto.mt910.GetMt910Request;
import com.ftn.model.dto.mt910.GetMt910Response;
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
    public GetMt910Response mt910(@RequestPayload GetMt910Request request) {
        final GetMt910Response response = new GetMt910Response();
        response.setMt910("Ok");
        return response;
    }
}

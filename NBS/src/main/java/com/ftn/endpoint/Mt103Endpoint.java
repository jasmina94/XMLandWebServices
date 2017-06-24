package com.ftn.endpoint;

import com.ftn.model.dto.mt103.GetMt103Request;
import com.ftn.model.dto.mt103.GetMt103Response;
import com.ftn.service.Mt103Service;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    Mt103Service mt103Service;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMt103Request")
    @ResponsePayload
    public GetMt103Response mt103(@RequestPayload GetMt103Request request) {
        final GetMt103Response response = new GetMt103Response();
        response.setMt103("Ok");
        mt103Service.process(request.getMt103());
        return response;
    }
}
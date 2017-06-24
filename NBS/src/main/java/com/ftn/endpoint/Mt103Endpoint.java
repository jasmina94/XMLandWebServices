package com.ftn.endpoint;

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

//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMt103Request")
//    @ResponsePayload
//    public Mt103Response mt103(@RequestPayload Mt103Request request) {
//        final Mt103Response response = new Mt103Response();
//        response.setMt103(request.getMt103());
//        mt103Service.process(request.getMt103());
//        return response;
//    }
}
package rs.ac.uns.ftn.endpoints;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import rs.ac.uns.ftn.mt103.GetMt103Request;
import rs.ac.uns.ftn.mt103.GetMt103Response;
import rs.ac.uns.ftn.repository.Mt103Repository;

/**
 * Created by Jasmina on 23/06/2017.
 */
@Endpoint
public class MT103Endpoint {

    private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/mt103";

    private Mt103Repository mt103Repository;

    @Autowired
    public MT103Endpoint(Mt103Repository mt103Repository){
        this.mt103Repository = mt103Repository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMt103Request")
    @ResponsePayload
    public GetMt103Response getMt103(@RequestPayload GetMt103Request request){
        GetMt103Response response = new GetMt103Response();
        response.setMt103(mt103Repository.findMt103(request.getMt103().getIdPoruke()));

        return response;
    }
}

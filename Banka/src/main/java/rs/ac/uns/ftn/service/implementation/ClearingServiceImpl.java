package rs.ac.uns.ftn.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import rs.ac.uns.ftn.model.dto.mt102.GetMt102Request;
import rs.ac.uns.ftn.model.dto.mt102.GetMt102Response;
import rs.ac.uns.ftn.model.dto.mt102.Mt102;
import rs.ac.uns.ftn.model.dto.mt103.GetMt103Response;
import rs.ac.uns.ftn.model.environment.EnvironmentProperties;
import rs.ac.uns.ftn.service.ClearingService;

/**
 * Created by zlatan on 6/25/17.
 */
@Service
public class ClearingServiceImpl extends WebServiceGatewaySupport implements ClearingService {

    @Autowired
    private EnvironmentProperties environmentProperties;

    @Override
    public void processMT102(Mt102 mt102) {

    }

    @Override
    public void sendMT102(Mt102 mt102) {
        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(GetMt102Request.class, GetMt102Response.class);
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);

        final GetMt102Request getMt102Request = new GetMt102Request();
        getMt102Request.setMt102(mt102);

        final GetMt102Response response = (GetMt102Response) getWebServiceTemplate().marshalSendAndReceive(environmentProperties.getNbsUrl(), getMt102Request);
    }
}

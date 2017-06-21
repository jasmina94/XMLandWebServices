package com.ftn;

import com.ftn.model.*;
import com.ftn.model.request.Mt102Request;
import com.ftn.model.request.Mt103Request;
import com.ftn.model.request.Mt900Request;
import com.ftn.model.request.Mt910Request;
import com.ftn.model.response.Mt102Response;
import com.ftn.model.response.Mt103Response;
import com.ftn.model.response.Mt900Response;
import com.ftn.model.response.Mt910Response;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Alex on 6/21/17.
 */
public class Client extends WebServiceGatewaySupport {

    public void mock() {

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//        marshaller.setClassesToBeBound(Mt103Request.class, Mt103Response.class);
//        marshaller.setClassesToBeBound(Mt900Request.class, Mt900Response.class);
//        marshaller.setClassesToBeBound(Mt910Request.class, Mt910Response.class);

        marshaller.setClassesToBeBound(Mt102Request.class, Mt102Response.class);

        setMarshaller(marshaller);
        setUnmarshaller(marshaller);

//        Mt103Request p = new Mt103Request();
//        Mt103 m = new Mt103();
//        m.setIdPoruke("testiram soap cb");
//        p.setMt103(m);

//        Mt900Request p = new Mt900Request();
//        Mt900 model = new Mt900();
//        model.setIdPoruke("testiram soap 910 cb");
//        p.setMt900(model);



//        Mt910Request p = new Mt910Request();
//        Mt910 model = new Mt910();
//        model.setIdPoruke("testiram soap 910 cb");
//        p.setMt910(model);

        Mt102Request p = new Mt102Request();
        Mt102 model = new Mt102();
        Mt102Zaglavlje zag = new Mt102Zaglavlje();
        model.setMt102Zaglavlje(zag);
        model.getMt102Zaglavlje().setIdPoruke("poruka zaglavlja 102");
        p.setMt102(model);


        String uri = "http://localhost:8094/ws";
        Object o = getWebServiceTemplate().marshalSendAndReceive(uri, p);
        Mt102Response response = (Mt102Response) o;
        System.out.println("**************************************");
        System.out.println(p);

        FileOutputStream os = null;
        try {
            os = new FileOutputStream("/Users/Alex/Desktop/xml.xml");
            getMarshaller().marshal(p, new StreamResult(os));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

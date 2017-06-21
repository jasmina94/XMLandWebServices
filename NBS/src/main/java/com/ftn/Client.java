package com.ftn;

import com.ftn.model.Mt103;
import com.ftn.model.request.Mt103Request;
import com.ftn.model.request.Mt103Response;
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
        marshaller.setClassesToBeBound(Mt103Request.class, Mt103Response.class);
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);

        Mt103Request p = new Mt103Request();
        Mt103 m = new Mt103();
        m.setIdPoruke("testiram soap cb");
        p.setMt103(m);

        String uri = "http://localhost:8094/ws";
        Object o = getWebServiceTemplate().marshalSendAndReceive(uri, p);
        Mt103Response response = (Mt103Response) o;
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

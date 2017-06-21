package com.ftn;

import com.ftn.model.Mt103;
import com.ftn.model.request.Mt103Request;
import com.ftn.model.request.Mt103Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@SpringBootApplication
public class NbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NbsApplication.class, args);

		final Client client = new Client();
		client.mock();
	}
}

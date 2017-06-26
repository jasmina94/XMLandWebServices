package rs.ac.uns.ftn.service;

import rs.ac.uns.ftn.model.dto.mt103.Mt103;
import rs.ac.uns.ftn.model.dto.mt900.Mt900;
import rs.ac.uns.ftn.model.dto.mt910.Mt910;

/**
 * Created by zlatan on 6/25/17.
 */
public interface RTGSService {

    void processMT103(Mt103 mt103);

    void sendMT103(Mt103 mt103);

    String processMT900(Mt900 mt900);

    String processMT910(Mt910 mt910);

    void save(Mt103 mt103);
}

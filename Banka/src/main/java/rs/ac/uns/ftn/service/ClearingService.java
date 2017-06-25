package rs.ac.uns.ftn.service;

import rs.ac.uns.ftn.model.dto.mt102.Mt102;

/**
 * Created by zlatan on 6/25/17.
 */
public interface ClearingService {

    void processMT102(Mt102 mt102);

    void sendMT102(Mt102 mt102);
}

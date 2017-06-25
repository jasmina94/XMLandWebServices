package com.ftn.service;

import com.ftn.model.dto.mt102.Mt102;
import com.ftn.model.dto.mt103.Mt103;

/**
 * Created by Alex on 6/24/17.
 */
public interface Mt910Service {

    void send(Mt103 mt103);

    void send(Mt102 mt102);
}

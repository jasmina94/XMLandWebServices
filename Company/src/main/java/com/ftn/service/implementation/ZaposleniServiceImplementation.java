package com.ftn.service.implementation;

import com.ftn.model.Zaposleni;
import com.ftn.model.dto.ZaposleniDTO;
import com.ftn.repository.ZaposleniDao;
import com.ftn.service.ZaposleniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Olivera on 17.6.2017..
 */
@Service
public class ZaposleniServiceImplementation implements ZaposleniService {

    private ZaposleniDao zaposleniDao;

    @Autowired
    public ZaposleniServiceImplementation(ZaposleniDao zaposleniDao) {
        this.zaposleniDao = zaposleniDao;
    }

    @Override
    public ZaposleniDTO read() {
        return null;
    }
}

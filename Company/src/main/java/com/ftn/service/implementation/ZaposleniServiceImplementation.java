package com.ftn.service.implementation;

import com.ftn.model.database.Zaposleni;
import com.ftn.model.dto.ZaposleniDTO;
import com.ftn.repository.ZaposleniDao;
import com.ftn.service.ZaposleniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Zaposleni zaposleni = zaposleniDao.findByKorisnickoIme(authentication.getName());
        return new ZaposleniDTO(zaposleni);
    }
}

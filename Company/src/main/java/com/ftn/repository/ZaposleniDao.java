package com.ftn.repository;

import com.ftn.model.Zaposleni;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Olivera on 17.6.2017..
 */
public interface ZaposleniDao extends JpaRepository<Zaposleni, Long> {

    <E extends Zaposleni> E findByKorisnickoIme(String korisnickoIme);

}

package com.ftn.repository;

import com.ftn.model.database.Zaposleni;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Alex on 6/17/17.
 */
public interface ZaposleniDao extends JpaRepository<Zaposleni, Long> {

    Zaposleni findByKorisnickoIme(String username);
}

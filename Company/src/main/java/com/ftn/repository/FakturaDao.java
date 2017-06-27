package com.ftn.repository;

import com.ftn.model.database.DnevnoStanjeRacuna;
import com.ftn.model.generated.faktura.Faktura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

/**
 * Created by JELENA on 19.6.2017.
 */

public interface FakturaDao extends JpaRepository<Faktura, Long> {

    Optional<Faktura> findById(Long id);

}

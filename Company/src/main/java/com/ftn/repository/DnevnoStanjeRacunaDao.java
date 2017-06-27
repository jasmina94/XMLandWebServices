package com.ftn.repository;

import com.ftn.model.database.DnevnoStanjeRacuna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

/**
 * Created by JELENA on 27.6.2017.
 */
public interface DnevnoStanjeRacunaDao extends JpaRepository<DnevnoStanjeRacuna, Long> {

    Optional<DnevnoStanjeRacuna> findById(Long id);

    Optional<DnevnoStanjeRacuna> findByDatum(Date date);

}

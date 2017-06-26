package com.ftn.repository;

import com.ftn.model.generated.tipovi.TPodaciSubjekt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by JELENA on 19.6.2017.
 */

public interface TPodaciSubjekatDao extends JpaRepository<TPodaciSubjekt, Long> {

    Optional<TPodaciSubjekt> findById(Long id);

    Optional<TPodaciSubjekt> findByPib(String pib);


}

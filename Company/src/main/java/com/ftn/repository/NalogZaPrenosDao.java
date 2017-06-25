package com.ftn.repository;

import com.ftn.model.generated.nalog_za_prenos.NalogZaPrenos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Olivera on 22.6.2017..
 */
public interface NalogZaPrenosDao extends JpaRepository<NalogZaPrenos, Long> {

    Optional<NalogZaPrenos> findById(Long id);
}

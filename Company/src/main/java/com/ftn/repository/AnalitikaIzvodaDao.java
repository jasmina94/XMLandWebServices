package com.ftn.repository;

import com.ftn.model.database.AnalitikaIzvoda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by JELENA on 27.6.2017.
 */
public interface AnalitikaIzvodaDao extends JpaRepository<AnalitikaIzvoda, Long> {

    Optional<AnalitikaIzvoda> findById(Long id);

}

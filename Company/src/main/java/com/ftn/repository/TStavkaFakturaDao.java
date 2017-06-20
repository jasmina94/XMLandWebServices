package com.ftn.repository;

import com.ftn.model.TStavkaFaktura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Olivera on 20.6.2017..
 */
public interface TStavkaFakturaDao extends JpaRepository<TStavkaFaktura, Long> {

    Optional<TStavkaFaktura> findById(Long id);
}

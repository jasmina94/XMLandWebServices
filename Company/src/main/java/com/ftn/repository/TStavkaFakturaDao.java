package com.ftn.repository;

import com.ftn.model.generated.tipovi.TStavkaFaktura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Olivera on 20.6.2017..
 */

public interface TStavkaFakturaDao extends JpaRepository<TStavkaFaktura, Long> {

    Optional<TStavkaFaktura> findById(Long id);

    //List<TStavkaFaktura> findByFakturaId(Long id);
}

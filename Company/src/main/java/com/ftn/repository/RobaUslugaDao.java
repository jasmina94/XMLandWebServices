package com.ftn.repository;

import com.ftn.model.database.RobaUsluga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by JELENA on 22.6.2017.
 */
public interface RobaUslugaDao extends JpaRepository<RobaUsluga, Long> {
    Optional<RobaUsluga> findById(Long id);

}

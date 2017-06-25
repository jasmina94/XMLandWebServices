package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by JELENA on 19.6.2017.
 */
public interface FakturaDao extends JpaRepository<Faktura, Long> {

    Optional<Faktura> findById(Long id);
}

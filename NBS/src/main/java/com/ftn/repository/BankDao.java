package com.ftn.repository;


import com.ftn.model.database.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Alex on 6/22/17.
 */
public interface BankDao extends JpaRepository<Bank, Long> {

    Optional<Bank> findBySwiftCode(String swiftCode);
}

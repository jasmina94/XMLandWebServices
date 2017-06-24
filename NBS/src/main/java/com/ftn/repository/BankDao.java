package com.ftn.repository;

import com.ftn.model.TPodaciBanka;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Alex on 6/22/17.
 */
public interface BankDao extends JpaRepository<TPodaciBanka, Long> {

    Optional<TPodaciBanka> findBySwiftKod(String swiftCode);
}

package rs.ac.uns.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.model.database.Mt102Model;

import java.util.Optional;

/**
 * Created by zlatan on 6/24/17.
 */
public interface Mt102Repository extends JpaRepository<Mt102Model, Long>{

    Optional<Mt102Model> findById(Long id);
}

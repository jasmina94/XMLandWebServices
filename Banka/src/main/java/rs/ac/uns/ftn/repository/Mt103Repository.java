package rs.ac.uns.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.model.database.Mt103Model;

import java.util.Optional;

/**
 * Created by zlatan on 6/24/17.
 */
public interface Mt103Repository extends JpaRepository<Mt103Model, Long> {

    Optional<Mt103Model> findById(Long id);

    Optional<Mt103Model> findByIdPoruke(String idPoruke);
}

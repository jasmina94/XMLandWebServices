package rs.ac.uns.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.model.database.PojedinacniNalogZaPlacanje;

import java.util.Optional;

/**
 * Created by zlatan on 6/24/17.
 */
public interface PojedinacniNalogZaPlacanjeRepository extends JpaRepository<PojedinacniNalogZaPlacanje, Long> {

    Optional<PojedinacniNalogZaPlacanje> findById(Long id);

}

package rs.ac.uns.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.model.database.Racun;

import java.util.Optional;

/**
 * Created by zlatan on 6/24/17.
 */
public interface RacunRepository extends JpaRepository<Racun, Long> {

    Optional<Racun> findById(Long id);

    Optional<Racun> findByBrojRacuna(String brojracuna);

}

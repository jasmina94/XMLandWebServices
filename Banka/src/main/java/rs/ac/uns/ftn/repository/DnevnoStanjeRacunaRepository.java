package rs.ac.uns.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension;
import rs.ac.uns.ftn.model.database.DnevnoStanjeRacuna;

import java.util.Optional;

/**
 * Created by zlatan on 6/24/17.
 */
public interface DnevnoStanjeRacunaRepository extends JpaRepository<DnevnoStanjeRacuna, Long> {

    Optional<DnevnoStanjeRacuna> findById(Long id);

}

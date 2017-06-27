package rs.ac.uns.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.model.database.Firma;

import java.util.Optional;

/**
 * Created by Jasmina on 27/06/2017.
 */
public interface FirmaRepository extends JpaRepository<Firma, Long>{

    Optional<Firma> findByBrojRacuna(String brojRacuna);
}

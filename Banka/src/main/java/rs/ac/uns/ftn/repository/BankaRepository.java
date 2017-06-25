package rs.ac.uns.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.model.database.Banka;

import java.util.Optional;

/**
 * Created by zlatan on 6/24/17.
 */
public interface BankaRepository extends JpaRepository<Banka, Long> {

    Optional<Banka> findById(Long id);
}

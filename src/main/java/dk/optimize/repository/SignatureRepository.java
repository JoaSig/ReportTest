package dk.optimize.repository;

import dk.optimize.domain.Signature;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Signature entity.
 */
public interface SignatureRepository extends JpaRepository<Signature,Long> {

}

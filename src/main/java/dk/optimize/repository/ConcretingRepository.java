package dk.optimize.repository;

import dk.optimize.domain.Concreting;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Concreting entity.
 */
public interface ConcretingRepository extends JpaRepository<Concreting,Long> {

}

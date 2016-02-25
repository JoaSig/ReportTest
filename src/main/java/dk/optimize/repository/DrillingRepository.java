package dk.optimize.repository;

import dk.optimize.domain.Drilling;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Drilling entity.
 */
public interface DrillingRepository extends JpaRepository<Drilling,Long> {

}

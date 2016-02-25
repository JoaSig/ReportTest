package dk.optimize.repository;

import dk.optimize.domain.SteelCage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SteelCage entity.
 */
public interface SteelCageRepository extends JpaRepository<SteelCage,Long> {

}

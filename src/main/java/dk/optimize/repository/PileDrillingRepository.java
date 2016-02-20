package dk.optimize.repository;

import dk.optimize.domain.PileDrilling;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PileDrilling entity.
 */
public interface PileDrillingRepository extends JpaRepository<PileDrilling,Long> {

    @Query("select pileDrilling from PileDrilling pileDrilling where pileDrilling.user.login = ?#{principal.username} order by pileDrilling.drillingStartDate desc")
    List<PileDrilling> findByUserIsCurrentUser();

}

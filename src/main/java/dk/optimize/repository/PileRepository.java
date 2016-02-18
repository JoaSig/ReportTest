package dk.optimize.repository;

import dk.optimize.domain.Pile;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pile entity.
 */
public interface PileRepository extends JpaRepository<Pile,Long> {

    @Query("select pile from Pile pile where pile.user.login = ?#{principal.username}")
    List<Pile> findByUserIsCurrentUser();

}

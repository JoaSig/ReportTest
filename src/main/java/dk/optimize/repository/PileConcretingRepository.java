package dk.optimize.repository;

import dk.optimize.domain.PileConcreting;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PileConcreting entity.
 */
public interface PileConcretingRepository extends JpaRepository<PileConcreting,Long> {

    @Query("select pileConcreting from PileConcreting pileConcreting where pileConcreting.user.login = ?#{principal.username} order by pileConcreting.concretingDateStart desc")
    List<PileConcreting> findByUserIsCurrentUser();

}

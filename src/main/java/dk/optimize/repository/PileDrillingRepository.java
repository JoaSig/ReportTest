package dk.optimize.repository;

import dk.optimize.domain.PileDrilling;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the PileDrilling entity.
 */
public interface PileDrillingRepository extends JpaRepository<PileDrilling, Long> {

    @Query("select pileDrilling from PileDrilling pileDrilling where pileDrilling.user.login = ?#{principal.username} order by pileDrilling.drillingId desc")
    List<PileDrilling> findByUserIsCurrentUser();

    List<PileDrilling> findAllByDrillingMachine(String drillingMachine);

    Page<PileDrilling> findAllByOrderByDrillingIdAsc(Pageable pageable);
}

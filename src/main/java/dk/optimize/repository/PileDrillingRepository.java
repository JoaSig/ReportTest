package dk.optimize.repository;

import dk.optimize.domain.PileDrilling;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the PileDrilling entity.
 */
public interface PileDrillingRepository extends JpaRepository<PileDrilling,Long> {

    @Query("select pileDrilling from PileDrilling pileDrilling where pileDrilling.user.login = ?#{principal.username} order by pileDrilling.drillingStartDate desc")
    List<PileDrilling> findByUserIsCurrentUser();

    Page<PileDrilling> findAllByOrderByDrillingStartDateDesc(Pageable pageable);

    List<PileDrilling> findAllByDrillingStartDateBetween(LocalDate firstDate, LocalDate secondDate);

    List<PileDrilling> findAllByDrillingStartDate(LocalDate firstDate);

    Page<PileDrilling> findAllByDrillingMachine(String drillingMachine, Pageable pageable);

    List<PileDrilling> findAllByDrillingMachine(String drillingMachine);

}

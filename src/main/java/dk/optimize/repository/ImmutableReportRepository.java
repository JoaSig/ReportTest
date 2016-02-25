package dk.optimize.repository;

import dk.optimize.domain.ImmutableReport;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ImmutableReport entity.
 */
public interface ImmutableReportRepository extends JpaRepository<ImmutableReport,Long> {

}

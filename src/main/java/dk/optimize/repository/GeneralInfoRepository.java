package dk.optimize.repository;

import dk.optimize.domain.GeneralInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GeneralInfo entity.
 */
public interface GeneralInfoRepository extends JpaRepository<GeneralInfo,Long> {

}

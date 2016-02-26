package dk.optimize.repository;

import dk.optimize.domain.ProjectInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProjectInfo entity.
 */
public interface ProjectInfoRepository extends JpaRepository<ProjectInfo,Long> {

    List<ProjectInfo> findByUserIsCurrentUser();
}

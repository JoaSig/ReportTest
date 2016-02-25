package dk.optimize.repository.search;

import dk.optimize.domain.ProjectInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ProjectInfo entity.
 */
public interface ProjectInfoSearchRepository extends ElasticsearchRepository<ProjectInfo, Long> {
}

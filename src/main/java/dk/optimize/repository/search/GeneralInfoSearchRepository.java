package dk.optimize.repository.search;

import dk.optimize.domain.GeneralInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the GeneralInfo entity.
 */
public interface GeneralInfoSearchRepository extends ElasticsearchRepository<GeneralInfo, Long> {
}

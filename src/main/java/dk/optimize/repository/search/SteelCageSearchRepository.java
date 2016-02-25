package dk.optimize.repository.search;

import dk.optimize.domain.SteelCage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SteelCage entity.
 */
public interface SteelCageSearchRepository extends ElasticsearchRepository<SteelCage, Long> {
}

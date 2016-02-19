package dk.optimize.repository.search;

import dk.optimize.domain.PileDrilling;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PileDrilling entity.
 */
public interface PileDrillingSearchRepository extends ElasticsearchRepository<PileDrilling, Long> {
}

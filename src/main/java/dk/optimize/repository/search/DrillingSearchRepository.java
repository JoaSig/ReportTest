package dk.optimize.repository.search;

import dk.optimize.domain.Drilling;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Drilling entity.
 */
public interface DrillingSearchRepository extends ElasticsearchRepository<Drilling, Long> {
}

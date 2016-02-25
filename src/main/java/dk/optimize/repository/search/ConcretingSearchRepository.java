package dk.optimize.repository.search;

import dk.optimize.domain.Concreting;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Concreting entity.
 */
public interface ConcretingSearchRepository extends ElasticsearchRepository<Concreting, Long> {
}

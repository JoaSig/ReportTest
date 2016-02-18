package dk.optimize.repository.search;

import dk.optimize.domain.Pile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Pile entity.
 */
public interface PileSearchRepository extends ElasticsearchRepository<Pile, Long> {
}

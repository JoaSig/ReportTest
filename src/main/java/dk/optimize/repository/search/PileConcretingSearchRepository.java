package dk.optimize.repository.search;

import dk.optimize.domain.PileConcreting;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PileConcreting entity.
 */
public interface PileConcretingSearchRepository extends ElasticsearchRepository<PileConcreting, Long> {
}

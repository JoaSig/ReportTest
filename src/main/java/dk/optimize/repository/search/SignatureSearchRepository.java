package dk.optimize.repository.search;

import dk.optimize.domain.Signature;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Signature entity.
 */
public interface SignatureSearchRepository extends ElasticsearchRepository<Signature, Long> {
}

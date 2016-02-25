package dk.optimize.repository.search;

import dk.optimize.domain.ImmutableReport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ImmutableReport entity.
 */
public interface ImmutableReportSearchRepository extends ElasticsearchRepository<ImmutableReport, Long> {
}

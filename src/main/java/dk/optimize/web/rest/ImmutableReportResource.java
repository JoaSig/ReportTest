package dk.optimize.web.rest;

import com.codahale.metrics.annotation.Timed;
import dk.optimize.domain.ImmutableReport;
import dk.optimize.repository.ImmutableReportRepository;
import dk.optimize.repository.search.ImmutableReportSearchRepository;
import dk.optimize.web.rest.util.HeaderUtil;
import dk.optimize.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ImmutableReport.
 */
@RestController
@RequestMapping("/api")
public class ImmutableReportResource {

    private final Logger log = LoggerFactory.getLogger(ImmutableReportResource.class);
        
    @Inject
    private ImmutableReportRepository immutableReportRepository;
    
    @Inject
    private ImmutableReportSearchRepository immutableReportSearchRepository;
    
    /**
     * POST  /immutableReports -> Create a new immutableReport.
     */
    @RequestMapping(value = "/immutableReports",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ImmutableReport> createImmutableReport(@RequestBody ImmutableReport immutableReport) throws URISyntaxException {
        log.debug("REST request to save ImmutableReport : {}", immutableReport);
        if (immutableReport.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("immutableReport", "idexists", "A new immutableReport cannot already have an ID")).body(null);
        }
        ImmutableReport result = immutableReportRepository.save(immutableReport);
        immutableReportSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/immutableReports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("immutableReport", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /immutableReports -> Updates an existing immutableReport.
     */
    @RequestMapping(value = "/immutableReports",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ImmutableReport> updateImmutableReport(@RequestBody ImmutableReport immutableReport) throws URISyntaxException {
        log.debug("REST request to update ImmutableReport : {}", immutableReport);
        if (immutableReport.getId() == null) {
            return createImmutableReport(immutableReport);
        }
        ImmutableReport result = immutableReportRepository.save(immutableReport);
        immutableReportSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("immutableReport", immutableReport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /immutableReports -> get all the immutableReports.
     */
    @RequestMapping(value = "/immutableReports",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ImmutableReport>> getAllImmutableReports(Pageable pageable, @RequestParam(required = false) String filter)
        throws URISyntaxException {
        if ("user-is-null".equals(filter)) {
            log.debug("REST request to get all ImmutableReports where user is null");
            return new ResponseEntity<>(StreamSupport
                .stream(immutableReportRepository.findAll().spliterator(), false)
                .filter(immutableReport -> immutableReport.getUser() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        log.debug("REST request to get a page of ImmutableReports");
        Page<ImmutableReport> page = immutableReportRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/immutableReports");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /immutableReports/:id -> get the "id" immutableReport.
     */
    @RequestMapping(value = "/immutableReports/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ImmutableReport> getImmutableReport(@PathVariable Long id) {
        log.debug("REST request to get ImmutableReport : {}", id);
        ImmutableReport immutableReport = immutableReportRepository.findOne(id);
        return Optional.ofNullable(immutableReport)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /immutableReports/:id -> delete the "id" immutableReport.
     */
    @RequestMapping(value = "/immutableReports/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteImmutableReport(@PathVariable Long id) {
        log.debug("REST request to delete ImmutableReport : {}", id);
        immutableReportRepository.delete(id);
        immutableReportSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("immutableReport", id.toString())).build();
    }

    /**
     * SEARCH  /_search/immutableReports/:query -> search for the immutableReport corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/immutableReports/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ImmutableReport> searchImmutableReports(@PathVariable String query) {
        log.debug("REST request to search ImmutableReports for query {}", query);
        return StreamSupport
            .stream(immutableReportSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

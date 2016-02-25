package dk.optimize.web.rest;

import com.codahale.metrics.annotation.Timed;
import dk.optimize.domain.Drilling;
import dk.optimize.repository.DrillingRepository;
import dk.optimize.repository.search.DrillingSearchRepository;
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
 * REST controller for managing Drilling.
 */
@RestController
@RequestMapping("/api")
public class DrillingResource {

    private final Logger log = LoggerFactory.getLogger(DrillingResource.class);
        
    @Inject
    private DrillingRepository drillingRepository;
    
    @Inject
    private DrillingSearchRepository drillingSearchRepository;
    
    /**
     * POST  /drillings -> Create a new drilling.
     */
    @RequestMapping(value = "/drillings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Drilling> createDrilling(@RequestBody Drilling drilling) throws URISyntaxException {
        log.debug("REST request to save Drilling : {}", drilling);
        if (drilling.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("drilling", "idexists", "A new drilling cannot already have an ID")).body(null);
        }
        Drilling result = drillingRepository.save(drilling);
        drillingSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/drillings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("drilling", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /drillings -> Updates an existing drilling.
     */
    @RequestMapping(value = "/drillings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Drilling> updateDrilling(@RequestBody Drilling drilling) throws URISyntaxException {
        log.debug("REST request to update Drilling : {}", drilling);
        if (drilling.getId() == null) {
            return createDrilling(drilling);
        }
        Drilling result = drillingRepository.save(drilling);
        drillingSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("drilling", drilling.getId().toString()))
            .body(result);
    }

    /**
     * GET  /drillings -> get all the drillings.
     */
    @RequestMapping(value = "/drillings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Drilling>> getAllDrillings(Pageable pageable, @RequestParam(required = false) String filter)
        throws URISyntaxException {
        if ("pile-is-null".equals(filter)) {
            log.debug("REST request to get all Drillings where pile is null");
            return new ResponseEntity<>(StreamSupport
                .stream(drillingRepository.findAll().spliterator(), false)
                .filter(drilling -> drilling.getPile() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Drillings");
        Page<Drilling> page = drillingRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/drillings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /drillings/:id -> get the "id" drilling.
     */
    @RequestMapping(value = "/drillings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Drilling> getDrilling(@PathVariable Long id) {
        log.debug("REST request to get Drilling : {}", id);
        Drilling drilling = drillingRepository.findOne(id);
        return Optional.ofNullable(drilling)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /drillings/:id -> delete the "id" drilling.
     */
    @RequestMapping(value = "/drillings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDrilling(@PathVariable Long id) {
        log.debug("REST request to delete Drilling : {}", id);
        drillingRepository.delete(id);
        drillingSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("drilling", id.toString())).build();
    }

    /**
     * SEARCH  /_search/drillings/:query -> search for the drilling corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/drillings/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Drilling> searchDrillings(@PathVariable String query) {
        log.debug("REST request to search Drillings for query {}", query);
        return StreamSupport
            .stream(drillingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

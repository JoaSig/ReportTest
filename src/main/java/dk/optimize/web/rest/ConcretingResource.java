package dk.optimize.web.rest;

import com.codahale.metrics.annotation.Timed;
import dk.optimize.domain.Concreting;
import dk.optimize.repository.ConcretingRepository;
import dk.optimize.repository.search.ConcretingSearchRepository;
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
 * REST controller for managing Concreting.
 */
@RestController
@RequestMapping("/api")
public class ConcretingResource {

    private final Logger log = LoggerFactory.getLogger(ConcretingResource.class);
        
    @Inject
    private ConcretingRepository concretingRepository;
    
    @Inject
    private ConcretingSearchRepository concretingSearchRepository;
    
    /**
     * POST  /concretings -> Create a new concreting.
     */
    @RequestMapping(value = "/concretings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Concreting> createConcreting(@RequestBody Concreting concreting) throws URISyntaxException {
        log.debug("REST request to save Concreting : {}", concreting);
        if (concreting.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("concreting", "idexists", "A new concreting cannot already have an ID")).body(null);
        }
        Concreting result = concretingRepository.save(concreting);
        concretingSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/concretings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("concreting", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /concretings -> Updates an existing concreting.
     */
    @RequestMapping(value = "/concretings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Concreting> updateConcreting(@RequestBody Concreting concreting) throws URISyntaxException {
        log.debug("REST request to update Concreting : {}", concreting);
        if (concreting.getId() == null) {
            return createConcreting(concreting);
        }
        Concreting result = concretingRepository.save(concreting);
        concretingSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("concreting", concreting.getId().toString()))
            .body(result);
    }

    /**
     * GET  /concretings -> get all the concretings.
     */
    @RequestMapping(value = "/concretings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Concreting>> getAllConcretings(Pageable pageable, @RequestParam(required = false) String filter)
        throws URISyntaxException {
        if ("pile-is-null".equals(filter)) {
            log.debug("REST request to get all Concretings where pile is null");
            return new ResponseEntity<>(StreamSupport
                .stream(concretingRepository.findAll().spliterator(), false)
                .filter(concreting -> concreting.getPile() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Concretings");
        Page<Concreting> page = concretingRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/concretings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /concretings/:id -> get the "id" concreting.
     */
    @RequestMapping(value = "/concretings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Concreting> getConcreting(@PathVariable Long id) {
        log.debug("REST request to get Concreting : {}", id);
        Concreting concreting = concretingRepository.findOne(id);
        return Optional.ofNullable(concreting)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /concretings/:id -> delete the "id" concreting.
     */
    @RequestMapping(value = "/concretings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteConcreting(@PathVariable Long id) {
        log.debug("REST request to delete Concreting : {}", id);
        concretingRepository.delete(id);
        concretingSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("concreting", id.toString())).build();
    }

    /**
     * SEARCH  /_search/concretings/:query -> search for the concreting corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/concretings/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Concreting> searchConcretings(@PathVariable String query) {
        log.debug("REST request to search Concretings for query {}", query);
        return StreamSupport
            .stream(concretingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

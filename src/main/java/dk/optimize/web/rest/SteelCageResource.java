package dk.optimize.web.rest;

import com.codahale.metrics.annotation.Timed;
import dk.optimize.domain.SteelCage;
import dk.optimize.repository.SteelCageRepository;
import dk.optimize.repository.search.SteelCageSearchRepository;
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
 * REST controller for managing SteelCage.
 */
@RestController
@RequestMapping("/api")
public class SteelCageResource {

    private final Logger log = LoggerFactory.getLogger(SteelCageResource.class);
        
    @Inject
    private SteelCageRepository steelCageRepository;
    
    @Inject
    private SteelCageSearchRepository steelCageSearchRepository;
    
    /**
     * POST  /steelCages -> Create a new steelCage.
     */
    @RequestMapping(value = "/steelCages",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SteelCage> createSteelCage(@RequestBody SteelCage steelCage) throws URISyntaxException {
        log.debug("REST request to save SteelCage : {}", steelCage);
        if (steelCage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("steelCage", "idexists", "A new steelCage cannot already have an ID")).body(null);
        }
        SteelCage result = steelCageRepository.save(steelCage);
        steelCageSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/steelCages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("steelCage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /steelCages -> Updates an existing steelCage.
     */
    @RequestMapping(value = "/steelCages",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SteelCage> updateSteelCage(@RequestBody SteelCage steelCage) throws URISyntaxException {
        log.debug("REST request to update SteelCage : {}", steelCage);
        if (steelCage.getId() == null) {
            return createSteelCage(steelCage);
        }
        SteelCage result = steelCageRepository.save(steelCage);
        steelCageSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("steelCage", steelCage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /steelCages -> get all the steelCages.
     */
    @RequestMapping(value = "/steelCages",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SteelCage>> getAllSteelCages(Pageable pageable, @RequestParam(required = false) String filter)
        throws URISyntaxException {
        if ("pile-is-null".equals(filter)) {
            log.debug("REST request to get all SteelCages where pile is null");
            return new ResponseEntity<>(StreamSupport
                .stream(steelCageRepository.findAll().spliterator(), false)
                .filter(steelCage -> steelCage.getPile() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        log.debug("REST request to get a page of SteelCages");
        Page<SteelCage> page = steelCageRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/steelCages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /steelCages/:id -> get the "id" steelCage.
     */
    @RequestMapping(value = "/steelCages/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SteelCage> getSteelCage(@PathVariable Long id) {
        log.debug("REST request to get SteelCage : {}", id);
        SteelCage steelCage = steelCageRepository.findOne(id);
        return Optional.ofNullable(steelCage)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /steelCages/:id -> delete the "id" steelCage.
     */
    @RequestMapping(value = "/steelCages/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSteelCage(@PathVariable Long id) {
        log.debug("REST request to delete SteelCage : {}", id);
        steelCageRepository.delete(id);
        steelCageSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("steelCage", id.toString())).build();
    }

    /**
     * SEARCH  /_search/steelCages/:query -> search for the steelCage corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/steelCages/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SteelCage> searchSteelCages(@PathVariable String query) {
        log.debug("REST request to search SteelCages for query {}", query);
        return StreamSupport
            .stream(steelCageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

package dk.optimize.web.rest;

import com.codahale.metrics.annotation.Timed;
import dk.optimize.domain.PileDrilling;
import dk.optimize.repository.PileDrillingRepository;
import dk.optimize.repository.search.PileDrillingSearchRepository;
import dk.optimize.security.AuthoritiesConstants;
import dk.optimize.security.SecurityUtils;
import dk.optimize.web.rest.util.HeaderUtil;
import dk.optimize.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.FacetedPageImpl;
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
 * REST controller for managing PileDrilling.
 */
@RestController
@RequestMapping("/api")
public class PileDrillingResource {

    private final Logger log = LoggerFactory.getLogger(PileDrillingResource.class);

    @Inject
    private PileDrillingRepository pileDrillingRepository;

    @Inject
    private PileDrillingSearchRepository pileDrillingSearchRepository;

    /**
     * POST  /pileDrillings -> Create a new pileDrilling.
     */
    @RequestMapping(value = "/pileDrillings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PileDrilling> createPileDrilling(@RequestBody PileDrilling pileDrilling) throws URISyntaxException {
        log.debug("REST request to save PileDrilling : {}", pileDrilling);
        if (pileDrilling.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pileDrilling", "idexists", "A new pileDrilling cannot already have an ID")).body(null);
        }
        PileDrilling result = pileDrillingRepository.save(pileDrilling);
        pileDrillingSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pileDrillings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pileDrilling", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pileDrillings -> Updates an existing pileDrilling.
     */
    @RequestMapping(value = "/pileDrillings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PileDrilling> updatePileDrilling(@RequestBody PileDrilling pileDrilling) throws URISyntaxException {
        log.debug("REST request to update PileDrilling : {}", pileDrilling);
        if (pileDrilling.getId() == null) {
            return createPileDrilling(pileDrilling);
        }
        PileDrilling result = pileDrillingRepository.save(pileDrilling);
        pileDrillingSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pileDrilling", pileDrilling.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pileDrillings -> get all the pileDrillings.
     */
    @RequestMapping(value = "/pileDrillings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PileDrilling>> getAllPileDrillings(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PileDrillings");
        Page<PileDrilling> page;
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            page = pileDrillingRepository.findAll(pageable);
        } else {
            List<PileDrilling> pileDrillingList = pileDrillingRepository.findByUserIsCurrentUser();
            page = new FacetedPageImpl<>(pileDrillingList);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pileDrillings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pileDrillings/:id -> get the "id" pileDrilling.
     */
    @RequestMapping(value = "/pileDrillings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PileDrilling> getPileDrilling(@PathVariable Long id) {
        log.debug("REST request to get PileDrilling : {}", id);
        PileDrilling pileDrilling = pileDrillingRepository.findOne(id);
        return Optional.ofNullable(pileDrilling)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pileDrillings/:id -> delete the "id" pileDrilling.
     */
    @RequestMapping(value = "/pileDrillings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePileDrilling(@PathVariable Long id) {
        log.debug("REST request to delete PileDrilling : {}", id);
        pileDrillingRepository.delete(id);
        pileDrillingSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pileDrilling", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pileDrillings/:query -> search for the pileDrilling corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pileDrillings/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PileDrilling> searchPileDrillings(@PathVariable String query) {
        log.debug("REST request to search PileDrillings for query {}", query);
        return StreamSupport
            .stream(pileDrillingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

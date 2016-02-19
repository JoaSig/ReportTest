package dk.optimize.web.rest;

import com.codahale.metrics.annotation.Timed;
import dk.optimize.domain.PileConcreting;
import dk.optimize.domain.User;
import dk.optimize.repository.PileConcretingRepository;
import dk.optimize.repository.search.PileConcretingSearchRepository;
import dk.optimize.security.AuthoritiesConstants;
import dk.optimize.security.SecurityUtils;
import dk.optimize.service.UserService;
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

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing PileConcreting.
 */
@RestController
@RequestMapping("/api")
public class PileConcretingResource {

    private final Logger log = LoggerFactory.getLogger(PileConcretingResource.class);

    @Inject
    private PileConcretingRepository pileConcretingRepository;

    @Inject
    private PileConcretingSearchRepository pileConcretingSearchRepository;

    @Inject
    private UserService userService;

    /**
     * POST  /pileConcretings -> Create a new pileConcreting.
     */
    @RequestMapping(value = "/pileConcretings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PileConcreting> createPileConcreting(@RequestBody PileConcreting pileConcreting) throws URISyntaxException {
        log.debug("REST request to save PileConcreting : {}", pileConcreting);
        if (pileConcreting.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pileConcreting", "idexists", "A new pileConcreting cannot already have an ID")).body(null);
        }
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            log.debug("No user passed in, using current user: {}", SecurityUtils.getCurrentUserLogin());
            pileConcreting.setUser(getUser(pileConcreting));
        }
        PileConcreting result = pileConcretingRepository.save(pileConcreting);
        pileConcretingSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pileConcretings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pileConcreting", result.getId().toString()))
            .body(result);
    }

    private User getUser(PileConcreting pileConcreting) {
        return userService != null ? userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()).get() : pileConcreting.getUser();
    }

    /**
     * PUT  /pileConcretings -> Updates an existing pileConcreting.
     */
    @RequestMapping(value = "/pileConcretings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PileConcreting> updatePileConcreting(@RequestBody PileConcreting pileConcreting) throws URISyntaxException {
        log.debug("REST request to update PileConcreting : {}", pileConcreting);
        if (pileConcreting.getId() == null) {
            return createPileConcreting(pileConcreting);
        }
        PileConcreting result = pileConcretingRepository.save(pileConcreting);
        pileConcretingSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pileConcreting", pileConcreting.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pileConcretings -> get all the pileConcretings.
     */
    @RequestMapping(value = "/pileConcretings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PileConcreting>> getAllPileConcretings(Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get a page of PileConcretings");
        Page<PileConcreting> page;
        try {
            if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                page = pileConcretingRepository.findAll(pageable);
            } else {
                List<PileConcreting> pileConcretingList = pileConcretingRepository.findByUserIsCurrentUser();
                page = new FacetedPageImpl<>(pileConcretingList);
            }
        } catch (Exception e) {
            log.error("Error trying to get current user - returning all instead: " + e.getLocalizedMessage(), e);
            page = pileConcretingRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pileConcretings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pileConcretings/:id -> get the "id" pileConcreting.
     */
    @RequestMapping(value = "/pileConcretings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PileConcreting> getPileConcreting(@PathVariable Long id) {
        log.debug("REST request to get PileConcreting : {}", id);
        PileConcreting pileConcreting = pileConcretingRepository.findOne(id);
        return Optional.ofNullable(pileConcreting)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pileConcretings/:id -> delete the "id" pileConcreting.
     */
    @RequestMapping(value = "/pileConcretings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePileConcreting(@PathVariable Long id) {
        log.debug("REST request to delete PileConcreting : {}", id);
        pileConcretingRepository.delete(id);
        pileConcretingSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pileConcreting", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pileConcretings/:query -> search for the pileConcreting corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pileConcretings/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PileConcreting> searchPileConcretings(@PathVariable String query) {
        log.debug("REST request to search PileConcretings for query {}", query);
        return StreamSupport
            .stream(pileConcretingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

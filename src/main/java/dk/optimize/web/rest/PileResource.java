package dk.optimize.web.rest;

import com.codahale.metrics.annotation.Timed;
import dk.optimize.domain.Pile;
import dk.optimize.repository.PileRepository;
import dk.optimize.repository.search.PileSearchRepository;
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
 * REST controller for managing Pile.
 */
@RestController
@RequestMapping("/api")
public class PileResource {

    private final Logger log = LoggerFactory.getLogger(PileResource.class);
        
    @Inject
    private PileRepository pileRepository;
    
    @Inject
    private PileSearchRepository pileSearchRepository;
    
    /**
     * POST  /piles -> Create a new pile.
     */
    @RequestMapping(value = "/piles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pile> createPile(@RequestBody Pile pile) throws URISyntaxException {
        log.debug("REST request to save Pile : {}", pile);
        if (pile.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pile", "idexists", "A new pile cannot already have an ID")).body(null);
        }
        Pile result = pileRepository.save(pile);
        pileSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/piles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pile", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /piles -> Updates an existing pile.
     */
    @RequestMapping(value = "/piles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pile> updatePile(@RequestBody Pile pile) throws URISyntaxException {
        log.debug("REST request to update Pile : {}", pile);
        if (pile.getId() == null) {
            return createPile(pile);
        }
        Pile result = pileRepository.save(pile);
        pileSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pile", pile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /piles -> get all the piles.
     */
    @RequestMapping(value = "/piles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Pile>> getAllPiles(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Piles");
        Page<Pile> page = pileRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/piles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /piles/:id -> get the "id" pile.
     */
    @RequestMapping(value = "/piles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pile> getPile(@PathVariable Long id) {
        log.debug("REST request to get Pile : {}", id);
        Pile pile = pileRepository.findOne(id);
        return Optional.ofNullable(pile)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /piles/:id -> delete the "id" pile.
     */
    @RequestMapping(value = "/piles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePile(@PathVariable Long id) {
        log.debug("REST request to delete Pile : {}", id);
        pileRepository.delete(id);
        pileSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pile", id.toString())).build();
    }

    /**
     * SEARCH  /_search/piles/:query -> search for the pile corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/piles/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Pile> searchPiles(@PathVariable String query) {
        log.debug("REST request to search Piles for query {}", query);
        return StreamSupport
            .stream(pileSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

package dk.optimize.web.rest;

import com.codahale.metrics.annotation.Timed;
import dk.optimize.domain.GeneralInfo;
import dk.optimize.repository.GeneralInfoRepository;
import dk.optimize.repository.search.GeneralInfoSearchRepository;
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
 * REST controller for managing GeneralInfo.
 */
@RestController
@RequestMapping("/api")
public class GeneralInfoResource {

    private final Logger log = LoggerFactory.getLogger(GeneralInfoResource.class);
        
    @Inject
    private GeneralInfoRepository generalInfoRepository;
    
    @Inject
    private GeneralInfoSearchRepository generalInfoSearchRepository;
    
    /**
     * POST  /generalInfos -> Create a new generalInfo.
     */
    @RequestMapping(value = "/generalInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GeneralInfo> createGeneralInfo(@RequestBody GeneralInfo generalInfo) throws URISyntaxException {
        log.debug("REST request to save GeneralInfo : {}", generalInfo);
        if (generalInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("generalInfo", "idexists", "A new generalInfo cannot already have an ID")).body(null);
        }
        GeneralInfo result = generalInfoRepository.save(generalInfo);
        generalInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/generalInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("generalInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /generalInfos -> Updates an existing generalInfo.
     */
    @RequestMapping(value = "/generalInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GeneralInfo> updateGeneralInfo(@RequestBody GeneralInfo generalInfo) throws URISyntaxException {
        log.debug("REST request to update GeneralInfo : {}", generalInfo);
        if (generalInfo.getId() == null) {
            return createGeneralInfo(generalInfo);
        }
        GeneralInfo result = generalInfoRepository.save(generalInfo);
        generalInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("generalInfo", generalInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /generalInfos -> get all the generalInfos.
     */
    @RequestMapping(value = "/generalInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<GeneralInfo>> getAllGeneralInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of GeneralInfos");
        Page<GeneralInfo> page = generalInfoRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/generalInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /generalInfos/:id -> get the "id" generalInfo.
     */
    @RequestMapping(value = "/generalInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GeneralInfo> getGeneralInfo(@PathVariable Long id) {
        log.debug("REST request to get GeneralInfo : {}", id);
        GeneralInfo generalInfo = generalInfoRepository.findOne(id);
        return Optional.ofNullable(generalInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /generalInfos/:id -> delete the "id" generalInfo.
     */
    @RequestMapping(value = "/generalInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGeneralInfo(@PathVariable Long id) {
        log.debug("REST request to delete GeneralInfo : {}", id);
        generalInfoRepository.delete(id);
        generalInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("generalInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/generalInfos/:query -> search for the generalInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/generalInfos/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GeneralInfo> searchGeneralInfos(@PathVariable String query) {
        log.debug("REST request to search GeneralInfos for query {}", query);
        return StreamSupport
            .stream(generalInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

package dk.optimize.web.rest;

import com.codahale.metrics.annotation.Timed;
import dk.optimize.domain.Signature;
import dk.optimize.repository.SignatureRepository;
import dk.optimize.repository.search.SignatureSearchRepository;
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
 * REST controller for managing Signature.
 */
@RestController
@RequestMapping("/api")
public class SignatureResource {

    private final Logger log = LoggerFactory.getLogger(SignatureResource.class);
        
    @Inject
    private SignatureRepository signatureRepository;
    
    @Inject
    private SignatureSearchRepository signatureSearchRepository;
    
    /**
     * POST  /signatures -> Create a new signature.
     */
    @RequestMapping(value = "/signatures",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Signature> createSignature(@RequestBody Signature signature) throws URISyntaxException {
        log.debug("REST request to save Signature : {}", signature);
        if (signature.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("signature", "idexists", "A new signature cannot already have an ID")).body(null);
        }
        Signature result = signatureRepository.save(signature);
        signatureSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/signatures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("signature", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /signatures -> Updates an existing signature.
     */
    @RequestMapping(value = "/signatures",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Signature> updateSignature(@RequestBody Signature signature) throws URISyntaxException {
        log.debug("REST request to update Signature : {}", signature);
        if (signature.getId() == null) {
            return createSignature(signature);
        }
        Signature result = signatureRepository.save(signature);
        signatureSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("signature", signature.getId().toString()))
            .body(result);
    }

    /**
     * GET  /signatures -> get all the signatures.
     */
    @RequestMapping(value = "/signatures",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Signature>> getAllSignatures(Pageable pageable, @RequestParam(required = false) String filter)
        throws URISyntaxException {
        if ("user-is-null".equals(filter)) {
            log.debug("REST request to get all Signatures where user is null");
            return new ResponseEntity<>(StreamSupport
                .stream(signatureRepository.findAll().spliterator(), false)
                .filter(signature -> signature.getUser() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Signatures");
        Page<Signature> page = signatureRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/signatures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /signatures/:id -> get the "id" signature.
     */
    @RequestMapping(value = "/signatures/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Signature> getSignature(@PathVariable Long id) {
        log.debug("REST request to get Signature : {}", id);
        Signature signature = signatureRepository.findOne(id);
        return Optional.ofNullable(signature)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /signatures/:id -> delete the "id" signature.
     */
    @RequestMapping(value = "/signatures/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSignature(@PathVariable Long id) {
        log.debug("REST request to delete Signature : {}", id);
        signatureRepository.delete(id);
        signatureSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("signature", id.toString())).build();
    }

    /**
     * SEARCH  /_search/signatures/:query -> search for the signature corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/signatures/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Signature> searchSignatures(@PathVariable String query) {
        log.debug("REST request to search Signatures for query {}", query);
        return StreamSupport
            .stream(signatureSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

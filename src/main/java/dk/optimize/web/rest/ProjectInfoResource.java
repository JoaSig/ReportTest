package dk.optimize.web.rest;

import com.codahale.metrics.annotation.Timed;
import dk.optimize.domain.ProjectInfo;
import dk.optimize.repository.ProjectInfoRepository;
import dk.optimize.repository.search.ProjectInfoSearchRepository;
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
 * REST controller for managing ProjectInfo.
 */
@RestController
@RequestMapping("/api")
public class ProjectInfoResource {

    private final Logger log = LoggerFactory.getLogger(ProjectInfoResource.class);
        
    @Inject
    private ProjectInfoRepository projectInfoRepository;
    
    @Inject
    private ProjectInfoSearchRepository projectInfoSearchRepository;
    
    /**
     * POST  /projectInfos -> Create a new projectInfo.
     */
    @RequestMapping(value = "/projectInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProjectInfo> createProjectInfo(@RequestBody ProjectInfo projectInfo) throws URISyntaxException {
        log.debug("REST request to save ProjectInfo : {}", projectInfo);
        if (projectInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("projectInfo", "idexists", "A new projectInfo cannot already have an ID")).body(null);
        }
        ProjectInfo result = projectInfoRepository.save(projectInfo);
        projectInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/projectInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("projectInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /projectInfos -> Updates an existing projectInfo.
     */
    @RequestMapping(value = "/projectInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProjectInfo> updateProjectInfo(@RequestBody ProjectInfo projectInfo) throws URISyntaxException {
        log.debug("REST request to update ProjectInfo : {}", projectInfo);
        if (projectInfo.getId() == null) {
            return createProjectInfo(projectInfo);
        }
        ProjectInfo result = projectInfoRepository.save(projectInfo);
        projectInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("projectInfo", projectInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /projectInfos -> get all the projectInfos.
     */
    @RequestMapping(value = "/projectInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ProjectInfo>> getAllProjectInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ProjectInfos");
        Page<ProjectInfo> page = projectInfoRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/projectInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /projectInfos/:id -> get the "id" projectInfo.
     */
    @RequestMapping(value = "/projectInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProjectInfo> getProjectInfo(@PathVariable Long id) {
        log.debug("REST request to get ProjectInfo : {}", id);
        ProjectInfo projectInfo = projectInfoRepository.findOne(id);
        return Optional.ofNullable(projectInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /projectInfos/:id -> delete the "id" projectInfo.
     */
    @RequestMapping(value = "/projectInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProjectInfo(@PathVariable Long id) {
        log.debug("REST request to delete ProjectInfo : {}", id);
        projectInfoRepository.delete(id);
        projectInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("projectInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/projectInfos/:query -> search for the projectInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/projectInfos/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProjectInfo> searchProjectInfos(@PathVariable String query) {
        log.debug("REST request to search ProjectInfos for query {}", query);
        return StreamSupport
            .stream(projectInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

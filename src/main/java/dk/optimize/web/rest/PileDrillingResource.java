package dk.optimize.web.rest;

import com.codahale.metrics.annotation.Timed;
import dk.optimize.domain.PileDrilling;
import dk.optimize.domain.User;
import dk.optimize.repository.PileDrillingRepository;
import dk.optimize.repository.search.PileDrillingSearchRepository;
import dk.optimize.security.AuthoritiesConstants;
import dk.optimize.security.SecurityUtils;
import dk.optimize.service.UserService;
import dk.optimize.web.rest.dto.PileDrillingByMachine;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

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

    @Inject
    private UserService userService;

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
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            log.debug("No user passed in, using current user: {}", SecurityUtils.getCurrentUserLogin());
            pileDrilling.setUser(getUser(pileDrilling));
        }
        PileDrilling result = pileDrillingRepository.save(pileDrilling);
        pileDrillingSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pileDrillings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pileDrilling", result.getId().toString()))
            .body(result);
    }

    private User getUser(PileDrilling pileDrilling) {
        return userService != null ? userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()).get() : pileDrilling.getUser();
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
    public ResponseEntity<List<PileDrilling>> getAllPileDrillings(Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get a page of PileDrillings");
        Page<PileDrilling> page;
        log.info("Currently logged in user: " + SecurityUtils.getCurrentUser());
        try {
            if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                page = pileDrillingRepository.findAllByOrderByDrillingStartDateDesc(pageable);
            } else {
                List<PileDrilling> pileDrillingList = pileDrillingRepository.findByUserIsCurrentUser();
                page = new FacetedPageImpl<>(pileDrillingList);
            }
        } catch (Exception e) {
            log.error("Error trying to get current user - returning all instead: " + e.getLocalizedMessage(), e);
            page = pileDrillingRepository.findAllByOrderByDrillingStartDateDesc(pageable);
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
     * GET  /pileDrillings -> get all the pileDrillings for the current week.
     //     */
//    @RequestMapping(value = "/pileDrillings-this-week")
//    @Timed
//    public ResponseEntity<PileDrillingsPerMonth> getPileDrillingsThisWeek() {
//        // Get current date
//        LocalDate now = new LocalDate();
//// Get first day of week
//        LocalDate startOfWeek = now
//            .withYearOfCentury(13)
//            .withMonthOfYear(DateTimeConstants.SEPTEMBER)
//            .withDayOfMonth(2);
//// Get last day of week
//        LocalDate endOfWeek = now
//            .withYearOfCentury(13)
//            .withMonthOfYear(DateTimeConstants.SEPTEMBER)
//            .withDayOfMonth(30);
//        log.debug("Looking for pileDrillings between: {} and {}", startOfWeek, endOfWeek);
//
//        Instant instant = Instant.ofEpochMilli(startOfWeek.toDate().getTime());
//        java.time.LocalDate start = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
//
//        Instant endInstant = Instant.ofEpochMilli(endOfWeek.toDate().getTime());
//        java.time.LocalDate end = LocalDateTime.ofInstant(endInstant, ZoneId.systemDefault()).toLocalDate();
//
//        List<PileDrilling> pileDrillingsStartDate = pileDrillingRepository.findAllByDrillingStartDate(start);
//        List<PileDrilling> pileDrillings = pileDrillingRepository.findAllByDrillingStartDateBetween(start, end);
//        // filter by current user and sum the pileDrillings
//        Integer numPoints = pileDrillings.stream()
//            .filter(p -> p.getUser().getLogin().equals(SecurityUtils.getCurrentUserLogin()))
//            .mapToInt(p -> p.getProjectDepth().intValue() + p.getDrillingEffectiveDepth().intValue())
//            .sum();
//        PileDrillingsPerMonth count = new PileDrillingsPerMonth(numPoints, start);
//        return new ResponseEntity<>(count, HttpStatus.OK);
//    }

    /**
     * GET  /pileDrillings -> get all the pileDrillings by machine.
     */
    @RequestMapping(value = "/util/machine",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Set<String>> getAllDrillingMachines() throws URISyntaxException {
        Set<String> drillingMachines = new HashSet<>();
        List<PileDrilling> pileDrillings = pileDrillingRepository.findAll();
        drillingMachines.addAll(pileDrillings.stream().map(PileDrilling::getDrillingMachine).collect(Collectors.toList()));
        return new ResponseEntity<>(drillingMachines, HttpStatus.OK);
    }

    /**
     * GET  /pileDrillings -> get all the pileDrillings by machine.
     */
    @RequestMapping(value = "/pileDrillings/machine/{drillingMachine}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PileDrillingByMachine> getPileDrillingsByMachine(@PathVariable String drillingMachine, Pageable pageable) throws URISyntaxException {
//        String machine = "SR-100 4075";
//        Page<PileDrilling> page = pileDrillingRepository.findAllByDrillingMachine(drillingMachine, pageable);
        List<PileDrilling> pileDrillings = pileDrillingRepository.findAllByDrillingMachine(drillingMachine);

//        List<PileDrilling> pileDrillings = page.getContent();

        // filter by current user and sum the pileDrillings
//        BigDecimal numPoints = pileDrillings.stream()
//            .filter(p -> p.getUser().getLogin().equals(SecurityUtils.getCurrentUserLogin()))
//            .mapToDouble(p -> p.getProjectDepth().intValueExact())
//            .sum();
//        PileDrillingsByMachine sum = new PileDrillingsByMachine(numPoints, machine);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pileDrillings/machine");

        Map<Long, Long> drillingMinutesMap = new HashMap<>();
        BigDecimal depthSum = BigDecimal.ZERO;
        long minuteSum = 0;
        for (PileDrilling pileDrilling : pileDrillings) {
            depthSum = depthSum.add(pileDrilling.getDrillingEffectiveDepth());
            minuteSum += getTotalDrillingMinutes(pileDrilling, drillingMinutesMap);
        }
        BigDecimal minSumDec = new BigDecimal(minuteSum);
        BigDecimal meterDrillPerHour = minSumDec.divide(depthSum, 3, RoundingMode.CEILING);
        PileDrillingByMachine sum = new PileDrillingByMachine(depthSum, drillingMachine, minuteSum, meterDrillPerHour, getFormatedTotalTime(minuteSum), pileDrillings, drillingMinutesMap);
        return new ResponseEntity<>(sum, HttpStatus.OK);
    }

    private long getTotalDrillingMinutes(PileDrilling pileDrilling, Map<Long, Long> drillingMinutesMap) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//        try {
//            Date startDate = dateFormat.parse(pileDrilling.getDrillingStartTime());
//            Instant instant = Instant.ofEpochMilli(pileDrilling.getDrillingStartTime().);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(instant.toEpochMilli());
        Date startDate = pileDrilling.getDrillingStartTime();
        Long startMs = startDate.getTime();

        Date endDate = pileDrilling.getDrillingEndTime();
//            Date endDate = dateFormat.parse(pileDrilling.getDrillingEndTime());
        Long endMs = endDate.getTime();

        long totalTime;
        if (endMs < startMs) {
            totalTime = startMs - endMs;
        } else {
            totalTime = endMs - startMs;
        }
        long minuteSum = totalTime / 1000 / 60;
        drillingMinutesMap.put(pileDrilling.getId(), minuteSum);
        //            long totalHr = totalTime / 1000 / 60 / 60;
        return minuteSum;
//        } catch (ParseException e) {
//            log.error("Error while parsing start or end time for PileDrilling: " + pileDrilling.toString(), e);
//        }
//        return 0;
    }

    public static String getFormatedTotalTime(long runtimeInMinutes) {
        long hrs = 0;
        long mins;
        if (runtimeInMinutes != 0) {
            if (runtimeInMinutes / 60 >= 1) {
                hrs = runtimeInMinutes / 60;
                mins = runtimeInMinutes - (hrs * 60);
            } else {
                mins = runtimeInMinutes;
            }
        } else {
            return "00:00:00";
        }

        String stringMins = String.valueOf(mins);
        if (stringMins.length() < 2) {
            stringMins = "0".concat(stringMins);
        }

        if (hrs >= 10) {
            return String.format("%d:%s:00", hrs, stringMins);
        } else {
            return String.format("0%d:%s:00", hrs, stringMins);
        }
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

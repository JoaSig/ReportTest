package dk.optimize.web.rest;

import dk.optimize.Application;
import dk.optimize.domain.PileDrilling;
import dk.optimize.repository.PileDrillingRepository;
import dk.optimize.repository.search.PileDrillingSearchRepository;

import org.joda.time.DateTimeConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PileDrillingResource REST controller.
 *
 * @see PileDrillingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PileDrillingResourceIntTest {

    private static final String DEFAULT_DRILLING_MACHINE = "AAAAA";
    private static final String UPDATED_DRILLING_MACHINE = "BBBBB";

    private static final BigDecimal DEFAULT_PROJECT_DEPTH = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROJECT_DEPTH = new BigDecimal(2);

    private static final BigDecimal DEFAULT_EFFECTIVE_DEPTH = new BigDecimal(1);
    private static final BigDecimal UPDATED_EFFECTIVE_DEPTH = new BigDecimal(2);

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_START_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_DRILLING_ID = 1L;
    private static final Long UPDATED_DRILLING_ID = 2L;

    @Inject
    private PileDrillingRepository pileDrillingRepository;

    @Inject
    private PileDrillingSearchRepository pileDrillingSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPileDrillingMockMvc;

    private PileDrilling pileDrilling;

    @Autowired
    public WebApplicationContext context;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PileDrillingResource pileDrillingResource = new PileDrillingResource();
        ReflectionTestUtils.setField(pileDrillingResource, "pileDrillingSearchRepository", pileDrillingSearchRepository);
        ReflectionTestUtils.setField(pileDrillingResource, "pileDrillingRepository", pileDrillingRepository);
        this.restPileDrillingMockMvc = MockMvcBuilders.standaloneSetup(pileDrillingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pileDrilling = new PileDrilling();
        pileDrilling.setDrillingMachine(DEFAULT_DRILLING_MACHINE);
        pileDrilling.setProjectDepth(DEFAULT_PROJECT_DEPTH);
        pileDrilling.setEffectiveDepth(DEFAULT_EFFECTIVE_DEPTH);
        pileDrilling.setStartDate(DEFAULT_START_DATE);
        pileDrilling.setEndDate(DEFAULT_END_DATE);
        pileDrilling.setStartTime(DEFAULT_START_TIME);
        pileDrilling.setEndTime(DEFAULT_END_TIME);
        pileDrilling.setDrillingId(DEFAULT_DRILLING_ID);
    }

    @Test
    @Transactional
    public void createPileDrilling() throws Exception {
        int databaseSizeBeforeCreate = pileDrillingRepository.findAll().size();

        // Create the PileDrilling

//        restPileDrillingMockMvc = MockMvcBuilders
//            .webAppContextSetup(context)
//            .apply(springSecurity())
//            .build();

        restPileDrillingMockMvc.perform(post("/api/pileDrillings")
            .with(user("user"))
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pileDrilling)))
            .andExpect(status().isCreated());

        // Validate the PileDrilling in the database
        List<PileDrilling> pileDrillings = pileDrillingRepository.findAll();
        assertThat(pileDrillings).hasSize(databaseSizeBeforeCreate + 1);
        PileDrilling testPileDrilling = pileDrillings.get(pileDrillings.size() - 1);
        assertThat(testPileDrilling.getDrillingMachine()).isEqualTo(DEFAULT_DRILLING_MACHINE);
        assertThat(testPileDrilling.getProjectDepth()).isEqualTo(DEFAULT_PROJECT_DEPTH);
        assertThat(testPileDrilling.getEffectiveDepth()).isEqualTo(DEFAULT_EFFECTIVE_DEPTH);
        assertThat(testPileDrilling.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPileDrilling.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPileDrilling.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testPileDrilling.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testPileDrilling.getDrillingId()).isEqualTo(DEFAULT_DRILLING_ID);
//        assertThat(testPileDrilling.getUser().getLogin()).isEqualTo("user");
    }

    @Test
    @Transactional
    public void getAllPileDrillings() throws Exception {
        // Initialize the database
        pileDrillingRepository.saveAndFlush(pileDrilling);

        // Get all the pileDrillings
        restPileDrillingMockMvc.perform(get("/api/pileDrillings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pileDrilling.getId().intValue())))
                .andExpect(jsonPath("$.[*].drillingMachine").value(hasItem(DEFAULT_DRILLING_MACHINE.toString())))
                .andExpect(jsonPath("$.[*].projectDepth").value(hasItem(DEFAULT_PROJECT_DEPTH.intValue())))
                .andExpect(jsonPath("$.[*].effectiveDepth").value(hasItem(DEFAULT_EFFECTIVE_DEPTH.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].drillingId").value(hasItem(DEFAULT_DRILLING_ID.intValue())));
    }

    @Test
    @Transactional
    public void getPileDrilling() throws Exception {
        // Initialize the database
        pileDrillingRepository.saveAndFlush(pileDrilling);

        // Get the pileDrilling
        restPileDrillingMockMvc.perform(get("/api/pileDrillings/{id}", pileDrilling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pileDrilling.getId().intValue()))
            .andExpect(jsonPath("$.drillingMachine").value(DEFAULT_DRILLING_MACHINE.toString()))
            .andExpect(jsonPath("$.projectDepth").value(DEFAULT_PROJECT_DEPTH.intValue()))
            .andExpect(jsonPath("$.effectiveDepth").value(DEFAULT_EFFECTIVE_DEPTH.intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.drillingId").value(DEFAULT_DRILLING_ID.intValue()));
    }

//    @Test
//    @Transactional
//    public void getDrillingsThisWeek() throws Exception {
//        org.joda.time.LocalDate now = new org.joda.time.LocalDate();
//// Get first day of week
//        org.joda.time.LocalDate startOfWeek = now
//            .withYearOfCentury(13)
//            .withMonthOfYear(DateTimeConstants.SEPTEMBER)
//            .withDayOfMonth(2);
//// Get last day of week
//        org.joda.time.LocalDate endOfWeek = now
//            .withYearOfCentury(13)
//            .withMonthOfYear(DateTimeConstants.SEPTEMBER)
//            .withDayOfMonth(30);
//
//        Instant instant = Instant.ofEpochMilli(startOfWeek.toDate().getTime());
//        java.time.LocalDate start = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
//
//        Instant endInstant = Instant.ofEpochMilli(endOfWeek.toDate().getTime());
//        java.time.LocalDate end = LocalDateTime.ofInstant(endInstant, ZoneId.systemDefault()).toLocalDate();
//
//        // create security-aware mockMvc
////        restPileDrillingMockMvc = MockMvcBuilders
////            .webAppContextSetup(context)
////            .apply(springSecurity())
////            .build();
//
//        // Get all the points
//        restPileDrillingMockMvc.perform(get("/api/pileDrillings")
//            .with(user("user").roles("USER")))
//            .andExpect(status().isOk())
//            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
////            .andExpect(jsonPath("$", hasSize(4))
//            ;
//
//        // Get the points for this week only
//        restPileDrillingMockMvc.perform(get("/api/pileDrillings-this-week")
//            .with(user("user").roles("USER")))
//            .andExpect(status().isOk())
//            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.month").value(start.getMonth().name()))
//            .andExpect(jsonPath("$.drillings").value(5));
//    }

    @Test
    public void testConvertingToTimeAndSubtract() throws Exception {
        String startTime = "11:06:00";
        String endTime = "13:30:00";

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

        Date startDate = df.parse(startTime);
        Long startMs = startDate.getTime();

        Date endDate = df.parse(endTime);
        Long endMs = endDate.getTime();

        long totalTime = endMs - startMs;

        long totalMin = totalTime / 1000 / 60;
        long totalHr = totalTime / 1000 / 60 / 60;

    }

//    @Test
//    @Transactional
//    public void getDrillingsForMachine() throws Exception {
//        // create security-aware mockMvc
//
////        restPileDrillingMockMvc.perform(get("/api/pileDrillings")
////            .with(user("user").roles("USER")))
////            .andExpect(status().isOk())
////            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//////            .andExpect(jsonPath("$", hasSize(4))
////            ;
//
//        // Get the drillings for this machine
//        restPileDrillingMockMvc.perform(get("/api/pileDrillings/machine/{drillingMachine}", "SR-100 4075")
//            .with(user("admin").roles("ADMIN")))
//            .andExpect(status().isOk())
//            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.machine").value("SR-100 4075"))
//            .andExpect(jsonPath("$.drillings").value(44));
//    }

//    @Test
//    @Transactional
//    public void getDAllDrillingMachines() throws Exception {
//        // create security-aware mockMvc
//        restPileDrillingMockMvc = MockMvcBuilders
//            .webAppContextSetup(context)
//            .apply(springSecurity())
//            .build();
//        // Get all drilling machines
//        restPileDrillingMockMvc.perform(get("/api/util/machine")
//            .with(user("admin").roles("ADMIN")))
//            .andExpect(status().isOk())
//            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$").value("SR-100 4075"));
//    }

    @Test
    @Transactional
    public void getNonExistingPileDrilling() throws Exception {
        // Get the pileDrilling
        restPileDrillingMockMvc.perform(get("/api/pileDrillings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePileDrilling() throws Exception {
        // Initialize the database
        pileDrillingRepository.saveAndFlush(pileDrilling);

		int databaseSizeBeforeUpdate = pileDrillingRepository.findAll().size();

        // Update the pileDrilling
        pileDrilling.setDrillingMachine(UPDATED_DRILLING_MACHINE);
        pileDrilling.setProjectDepth(UPDATED_PROJECT_DEPTH);
        pileDrilling.setEffectiveDepth(UPDATED_EFFECTIVE_DEPTH);
        pileDrilling.setStartDate(UPDATED_START_DATE);
        pileDrilling.setEndDate(UPDATED_END_DATE);
        pileDrilling.setStartTime(UPDATED_START_TIME);
        pileDrilling.setEndTime(UPDATED_END_TIME);
        pileDrilling.setDrillingId(UPDATED_DRILLING_ID);

        restPileDrillingMockMvc.perform(put("/api/pileDrillings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pileDrilling)))
            .andExpect(status().isOk());

        // Validate the PileDrilling in the database
        List<PileDrilling> pileDrillings = pileDrillingRepository.findAll();
        assertThat(pileDrillings).hasSize(databaseSizeBeforeUpdate);
        PileDrilling testPileDrilling = pileDrillings.get(pileDrillings.size() - 1);
        assertThat(testPileDrilling.getDrillingMachine()).isEqualTo(UPDATED_DRILLING_MACHINE);
        assertThat(testPileDrilling.getProjectDepth()).isEqualTo(UPDATED_PROJECT_DEPTH);
        assertThat(testPileDrilling.getEffectiveDepth()).isEqualTo(UPDATED_EFFECTIVE_DEPTH);
        assertThat(testPileDrilling.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPileDrilling.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPileDrilling.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testPileDrilling.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testPileDrilling.getDrillingId()).isEqualTo(UPDATED_DRILLING_ID);
    }

    @Test
    @Transactional
    public void deletePileDrilling() throws Exception {
        // Initialize the database
        pileDrillingRepository.saveAndFlush(pileDrilling);

        int databaseSizeBeforeDelete = pileDrillingRepository.findAll().size();

        // Get the pileDrilling
        restPileDrillingMockMvc.perform(delete("/api/pileDrillings/{id}", pileDrilling.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PileDrilling> pileDrillings = pileDrillingRepository.findAll();
        assertThat(pileDrillings).hasSize(databaseSizeBeforeDelete - 1);
    }
}

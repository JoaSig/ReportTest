package dk.optimize.web.rest;

import dk.optimize.Application;
import dk.optimize.domain.PileDrilling;
import dk.optimize.repository.PileDrillingRepository;
import dk.optimize.repository.search.PileDrillingSearchRepository;
import org.joda.time.DateTimeConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

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

    private static final BigDecimal DEFAULT_PROJECT_DRILLING_DEPTH = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROJECT_DRILLING_DEPTH = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DRILLING_EFFECTIVE_DEPTH = new BigDecimal(1);
    private static final BigDecimal UPDATED_DRILLING_EFFECTIVE_DEPTH = new BigDecimal(2);

    private static final LocalDate DEFAULT_DRILLING_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DRILLING_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DRILLING_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DRILLING_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_DRILLING_START_TIME = "AAAAA";
    private static final String UPDATED_DRILLING_START_TIME = "BBBBB";
    private static final String DEFAULT_DRILLING_END_TIME = "AAAAA";
    private static final String UPDATED_DRILLING_END_TIME = "BBBBB";

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
        pileDrilling.setProjectDrillingDepth(DEFAULT_PROJECT_DRILLING_DEPTH);
        pileDrilling.setDrillingEffectiveDepth(DEFAULT_DRILLING_EFFECTIVE_DEPTH);
        pileDrilling.setDrillingStartDate(DEFAULT_DRILLING_START_DATE);
        pileDrilling.setDrillingEndDate(DEFAULT_DRILLING_END_DATE);
        pileDrilling.setDrillingStartTime(DEFAULT_DRILLING_START_TIME);
        pileDrilling.setDrillingEndTime(DEFAULT_DRILLING_END_TIME);
    }

    @Test
    @Transactional
    public void createPileDrilling() throws Exception {
        int databaseSizeBeforeCreate = pileDrillingRepository.findAll().size();

        // Create the PileDrilling

        restPileDrillingMockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();

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
        assertThat(testPileDrilling.getProjectDrillingDepth()).isEqualTo(DEFAULT_PROJECT_DRILLING_DEPTH);
        assertThat(testPileDrilling.getDrillingEffectiveDepth()).isEqualTo(DEFAULT_DRILLING_EFFECTIVE_DEPTH);
        assertThat(testPileDrilling.getDrillingStartDate()).isEqualTo(DEFAULT_DRILLING_START_DATE);
        assertThat(testPileDrilling.getDrillingEndDate()).isEqualTo(DEFAULT_DRILLING_END_DATE);
        assertThat(testPileDrilling.getDrillingStartTime()).isEqualTo(DEFAULT_DRILLING_START_TIME);
        assertThat(testPileDrilling.getDrillingEndTime()).isEqualTo(DEFAULT_DRILLING_END_TIME);
        assertThat(testPileDrilling.getUser().getLogin()).isEqualTo("user");
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
            .andExpect(jsonPath("$.[*].projectDrillingDepth").value(hasItem(DEFAULT_PROJECT_DRILLING_DEPTH.intValue())))
            .andExpect(jsonPath("$.[*].drillingEffectiveDepth").value(hasItem(DEFAULT_DRILLING_EFFECTIVE_DEPTH.intValue())))
            .andExpect(jsonPath("$.[*].drillingStartDate").value(hasItem(DEFAULT_DRILLING_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].drillingEndDate").value(hasItem(DEFAULT_DRILLING_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].drillingStartTime").value(hasItem(DEFAULT_DRILLING_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].drillingEndTime").value(hasItem(DEFAULT_DRILLING_END_TIME.toString())));
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
            .andExpect(jsonPath("$.projectDrillingDepth").value(DEFAULT_PROJECT_DRILLING_DEPTH.intValue()))
            .andExpect(jsonPath("$.drillingEffectiveDepth").value(DEFAULT_DRILLING_EFFECTIVE_DEPTH.intValue()))
            .andExpect(jsonPath("$.drillingStartDate").value(DEFAULT_DRILLING_START_DATE.toString()))
            .andExpect(jsonPath("$.drillingEndDate").value(DEFAULT_DRILLING_END_DATE.toString()))
            .andExpect(jsonPath("$.drillingStartTime").value(DEFAULT_DRILLING_START_TIME.toString()))
            .andExpect(jsonPath("$.drillingEndTime").value(DEFAULT_DRILLING_END_TIME.toString()));
    }

    @Test
    @Transactional
    public void getPointsThisWeek() throws Exception {
        LocalDate today = LocalDate.now();
        LocalDate thisMonday = today.withDayOfMonth(DateTimeConstants.MONDAY);
        LocalDate lastMonday = thisMonday.minusWeeks(1);
        // create security-aware mockMvc
        // Get all the points
        restPileDrillingMockMvc.perform(get("/api/pileDrillings")
            .with(user("user").roles("USER")))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$", hasSize(4))
            ;
    }

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
        pileDrilling.setProjectDrillingDepth(UPDATED_PROJECT_DRILLING_DEPTH);
        pileDrilling.setDrillingEffectiveDepth(UPDATED_DRILLING_EFFECTIVE_DEPTH);
        pileDrilling.setDrillingStartDate(UPDATED_DRILLING_START_DATE);
        pileDrilling.setDrillingEndDate(UPDATED_DRILLING_END_DATE);
        pileDrilling.setDrillingStartTime(UPDATED_DRILLING_START_TIME);
        pileDrilling.setDrillingEndTime(UPDATED_DRILLING_END_TIME);

        restPileDrillingMockMvc.perform(put("/api/pileDrillings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pileDrilling)))
            .andExpect(status().isOk());

        // Validate the PileDrilling in the database
        List<PileDrilling> pileDrillings = pileDrillingRepository.findAll();
        assertThat(pileDrillings).hasSize(databaseSizeBeforeUpdate);
        PileDrilling testPileDrilling = pileDrillings.get(pileDrillings.size() - 1);
        assertThat(testPileDrilling.getDrillingMachine()).isEqualTo(UPDATED_DRILLING_MACHINE);
        assertThat(testPileDrilling.getProjectDrillingDepth()).isEqualTo(UPDATED_PROJECT_DRILLING_DEPTH);
        assertThat(testPileDrilling.getDrillingEffectiveDepth()).isEqualTo(UPDATED_DRILLING_EFFECTIVE_DEPTH);
        assertThat(testPileDrilling.getDrillingStartDate()).isEqualTo(UPDATED_DRILLING_START_DATE);
        assertThat(testPileDrilling.getDrillingEndDate()).isEqualTo(UPDATED_DRILLING_END_DATE);
        assertThat(testPileDrilling.getDrillingStartTime()).isEqualTo(UPDATED_DRILLING_START_TIME);
        assertThat(testPileDrilling.getDrillingEndTime()).isEqualTo(UPDATED_DRILLING_END_TIME);
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

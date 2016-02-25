package dk.optimize.web.rest;

import dk.optimize.Application;
import dk.optimize.domain.Drilling;
import dk.optimize.repository.DrillingRepository;
import dk.optimize.repository.search.DrillingSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the DrillingResource REST controller.
 *
 * @see DrillingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DrillingResourceIntTest {

    private static final String DEFAULT_PILE_TYPE = "AAAAA";
    private static final String UPDATED_PILE_TYPE = "BBBBB";
    private static final String DEFAULT_PILING_RIG_TYPE = "AAAAA";
    private static final String UPDATED_PILING_RIG_TYPE = "BBBBB";

    private static final Integer DEFAULT_PILE_NR = 1;
    private static final Integer UPDATED_PILE_NR = 2;

    private static final BigDecimal DEFAULT_PILE_LENGTH = new BigDecimal(1);
    private static final BigDecimal UPDATED_PILE_LENGTH = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DIAMETER = new BigDecimal(1);
    private static final BigDecimal UPDATED_DIAMETER = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOP_GUIDE_ELEVATION = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOP_GUIDE_ELEVATION = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PILE_TOP_LEVEL = new BigDecimal(1);
    private static final BigDecimal UPDATED_PILE_TOP_LEVEL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PILE_CUTOFF_ELEVATION = new BigDecimal(1);
    private static final BigDecimal UPDATED_PILE_CUTOFF_ELEVATION = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PILE_TOE_LEVEL = new BigDecimal(1);
    private static final BigDecimal UPDATED_PILE_TOE_LEVEL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CASING_DEVIATION = new BigDecimal(1);
    private static final BigDecimal UPDATED_CASING_DEVIATION = new BigDecimal(2);

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_START_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_TIME = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    @Inject
    private DrillingRepository drillingRepository;

    @Inject
    private DrillingSearchRepository drillingSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDrillingMockMvc;

    private Drilling drilling;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DrillingResource drillingResource = new DrillingResource();
        ReflectionTestUtils.setField(drillingResource, "drillingSearchRepository", drillingSearchRepository);
        ReflectionTestUtils.setField(drillingResource, "drillingRepository", drillingRepository);
        this.restDrillingMockMvc = MockMvcBuilders.standaloneSetup(drillingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        drilling = new Drilling();
        drilling.setPileType(DEFAULT_PILE_TYPE);
        drilling.setPilingRigType(DEFAULT_PILING_RIG_TYPE);
        drilling.setPileNr(DEFAULT_PILE_NR);
        drilling.setPileLength(DEFAULT_PILE_LENGTH);
        drilling.setDiameter(DEFAULT_DIAMETER);
        drilling.setTopGuideElevation(DEFAULT_TOP_GUIDE_ELEVATION);
        drilling.setPileTopLevel(DEFAULT_PILE_TOP_LEVEL);
        drilling.setPileCutoffElevation(DEFAULT_PILE_CUTOFF_ELEVATION);
        drilling.setPileToeLevel(DEFAULT_PILE_TOE_LEVEL);
        drilling.setCasingDeviation(DEFAULT_CASING_DEVIATION);
        drilling.setStartDate(DEFAULT_START_DATE);
        drilling.setEndDate(DEFAULT_END_DATE);
        drilling.setStartTime(DEFAULT_START_TIME);
        drilling.setEndTime(DEFAULT_END_TIME);
        drilling.setComment(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createDrilling() throws Exception {
        int databaseSizeBeforeCreate = drillingRepository.findAll().size();

        // Create the Drilling

        restDrillingMockMvc.perform(post("/api/drillings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(drilling)))
                .andExpect(status().isCreated());

        // Validate the Drilling in the database
        List<Drilling> drillings = drillingRepository.findAll();
        assertThat(drillings).hasSize(databaseSizeBeforeCreate + 1);
        Drilling testDrilling = drillings.get(drillings.size() - 1);
        assertThat(testDrilling.getPileType()).isEqualTo(DEFAULT_PILE_TYPE);
        assertThat(testDrilling.getPilingRigType()).isEqualTo(DEFAULT_PILING_RIG_TYPE);
        assertThat(testDrilling.getPileNr()).isEqualTo(DEFAULT_PILE_NR);
        assertThat(testDrilling.getPileLength()).isEqualTo(DEFAULT_PILE_LENGTH);
        assertThat(testDrilling.getDiameter()).isEqualTo(DEFAULT_DIAMETER);
        assertThat(testDrilling.getTopGuideElevation()).isEqualTo(DEFAULT_TOP_GUIDE_ELEVATION);
        assertThat(testDrilling.getPileTopLevel()).isEqualTo(DEFAULT_PILE_TOP_LEVEL);
        assertThat(testDrilling.getPileCutoffElevation()).isEqualTo(DEFAULT_PILE_CUTOFF_ELEVATION);
        assertThat(testDrilling.getPileToeLevel()).isEqualTo(DEFAULT_PILE_TOE_LEVEL);
        assertThat(testDrilling.getCasingDeviation()).isEqualTo(DEFAULT_CASING_DEVIATION);
        assertThat(testDrilling.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testDrilling.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testDrilling.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testDrilling.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testDrilling.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void getAllDrillings() throws Exception {
        // Initialize the database
        drillingRepository.saveAndFlush(drilling);

        // Get all the drillings
        restDrillingMockMvc.perform(get("/api/drillings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(drilling.getId().intValue())))
                .andExpect(jsonPath("$.[*].pileType").value(hasItem(DEFAULT_PILE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].pilingRigType").value(hasItem(DEFAULT_PILING_RIG_TYPE.toString())))
                .andExpect(jsonPath("$.[*].pileNr").value(hasItem(DEFAULT_PILE_NR)))
                .andExpect(jsonPath("$.[*].pileLength").value(hasItem(DEFAULT_PILE_LENGTH.intValue())))
                .andExpect(jsonPath("$.[*].diameter").value(hasItem(DEFAULT_DIAMETER.intValue())))
                .andExpect(jsonPath("$.[*].topGuideElevation").value(hasItem(DEFAULT_TOP_GUIDE_ELEVATION.intValue())))
                .andExpect(jsonPath("$.[*].pileTopLevel").value(hasItem(DEFAULT_PILE_TOP_LEVEL.intValue())))
                .andExpect(jsonPath("$.[*].pileCutoffElevation").value(hasItem(DEFAULT_PILE_CUTOFF_ELEVATION.intValue())))
                .andExpect(jsonPath("$.[*].pileToeLevel").value(hasItem(DEFAULT_PILE_TOE_LEVEL.intValue())))
                .andExpect(jsonPath("$.[*].casingDeviation").value(hasItem(DEFAULT_CASING_DEVIATION.intValue())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
                .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getDrilling() throws Exception {
        // Initialize the database
        drillingRepository.saveAndFlush(drilling);

        // Get the drilling
        restDrillingMockMvc.perform(get("/api/drillings/{id}", drilling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(drilling.getId().intValue()))
            .andExpect(jsonPath("$.pileType").value(DEFAULT_PILE_TYPE.toString()))
            .andExpect(jsonPath("$.pilingRigType").value(DEFAULT_PILING_RIG_TYPE.toString()))
            .andExpect(jsonPath("$.pileNr").value(DEFAULT_PILE_NR))
            .andExpect(jsonPath("$.pileLength").value(DEFAULT_PILE_LENGTH.intValue()))
            .andExpect(jsonPath("$.diameter").value(DEFAULT_DIAMETER.intValue()))
            .andExpect(jsonPath("$.topGuideElevation").value(DEFAULT_TOP_GUIDE_ELEVATION.intValue()))
            .andExpect(jsonPath("$.pileTopLevel").value(DEFAULT_PILE_TOP_LEVEL.intValue()))
            .andExpect(jsonPath("$.pileCutoffElevation").value(DEFAULT_PILE_CUTOFF_ELEVATION.intValue()))
            .andExpect(jsonPath("$.pileToeLevel").value(DEFAULT_PILE_TOE_LEVEL.intValue()))
            .andExpect(jsonPath("$.casingDeviation").value(DEFAULT_CASING_DEVIATION.intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDrilling() throws Exception {
        // Get the drilling
        restDrillingMockMvc.perform(get("/api/drillings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrilling() throws Exception {
        // Initialize the database
        drillingRepository.saveAndFlush(drilling);

		int databaseSizeBeforeUpdate = drillingRepository.findAll().size();

        // Update the drilling
        drilling.setPileType(UPDATED_PILE_TYPE);
        drilling.setPilingRigType(UPDATED_PILING_RIG_TYPE);
        drilling.setPileNr(UPDATED_PILE_NR);
        drilling.setPileLength(UPDATED_PILE_LENGTH);
        drilling.setDiameter(UPDATED_DIAMETER);
        drilling.setTopGuideElevation(UPDATED_TOP_GUIDE_ELEVATION);
        drilling.setPileTopLevel(UPDATED_PILE_TOP_LEVEL);
        drilling.setPileCutoffElevation(UPDATED_PILE_CUTOFF_ELEVATION);
        drilling.setPileToeLevel(UPDATED_PILE_TOE_LEVEL);
        drilling.setCasingDeviation(UPDATED_CASING_DEVIATION);
        drilling.setStartDate(UPDATED_START_DATE);
        drilling.setEndDate(UPDATED_END_DATE);
        drilling.setStartTime(UPDATED_START_TIME);
        drilling.setEndTime(UPDATED_END_TIME);
        drilling.setComment(UPDATED_COMMENT);

        restDrillingMockMvc.perform(put("/api/drillings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(drilling)))
                .andExpect(status().isOk());

        // Validate the Drilling in the database
        List<Drilling> drillings = drillingRepository.findAll();
        assertThat(drillings).hasSize(databaseSizeBeforeUpdate);
        Drilling testDrilling = drillings.get(drillings.size() - 1);
        assertThat(testDrilling.getPileType()).isEqualTo(UPDATED_PILE_TYPE);
        assertThat(testDrilling.getPilingRigType()).isEqualTo(UPDATED_PILING_RIG_TYPE);
        assertThat(testDrilling.getPileNr()).isEqualTo(UPDATED_PILE_NR);
        assertThat(testDrilling.getPileLength()).isEqualTo(UPDATED_PILE_LENGTH);
        assertThat(testDrilling.getDiameter()).isEqualTo(UPDATED_DIAMETER);
        assertThat(testDrilling.getTopGuideElevation()).isEqualTo(UPDATED_TOP_GUIDE_ELEVATION);
        assertThat(testDrilling.getPileTopLevel()).isEqualTo(UPDATED_PILE_TOP_LEVEL);
        assertThat(testDrilling.getPileCutoffElevation()).isEqualTo(UPDATED_PILE_CUTOFF_ELEVATION);
        assertThat(testDrilling.getPileToeLevel()).isEqualTo(UPDATED_PILE_TOE_LEVEL);
        assertThat(testDrilling.getCasingDeviation()).isEqualTo(UPDATED_CASING_DEVIATION);
        assertThat(testDrilling.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testDrilling.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testDrilling.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testDrilling.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testDrilling.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void deleteDrilling() throws Exception {
        // Initialize the database
        drillingRepository.saveAndFlush(drilling);

		int databaseSizeBeforeDelete = drillingRepository.findAll().size();

        // Get the drilling
        restDrillingMockMvc.perform(delete("/api/drillings/{id}", drilling.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Drilling> drillings = drillingRepository.findAll();
        assertThat(drillings).hasSize(databaseSizeBeforeDelete - 1);
    }
}

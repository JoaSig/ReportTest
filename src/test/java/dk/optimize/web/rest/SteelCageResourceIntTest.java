package dk.optimize.web.rest;

import dk.optimize.Application;
import dk.optimize.domain.SteelCage;
import dk.optimize.repository.SteelCageRepository;
import dk.optimize.repository.search.SteelCageSearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SteelCageResource REST controller.
 *
 * @see SteelCageResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SteelCageResourceIntTest {


    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_ID_CAGE = "AAAAA";
    private static final String UPDATED_ID_CAGE = "BBBBB";

    private static final Boolean DEFAULT_SONIC_PIPES_COMPLIANCE = false;
    private static final Boolean UPDATED_SONIC_PIPES_COMPLIANCE = true;

    private static final Boolean DEFAULT_WATER_AND_CAPPING_SONIC_PIPE_FILLING = false;
    private static final Boolean UPDATED_WATER_AND_CAPPING_SONIC_PIPE_FILLING = true;

    private static final Boolean DEFAULT_OVERLAPPING_COMPLIANCE = false;
    private static final Boolean UPDATED_OVERLAPPING_COMPLIANCE = true;

    private static final Boolean DEFAULT_SPACER_POSITION_COMPLIANCE = false;
    private static final Boolean UPDATED_SPACER_POSITION_COMPLIANCE = true;

    private static final Boolean DEFAULT_DISTANCE_BETWEEN_CAGE_TOP_AND_GUIDE_WALL_EDGE = false;
    private static final Boolean UPDATED_DISTANCE_BETWEEN_CAGE_TOP_AND_GUIDE_WALL_EDGE = true;

    private static final Boolean DEFAULT_VERTICALITY_COMPLIANCE = false;
    private static final Boolean UPDATED_VERTICALITY_COMPLIANCE = true;

    private static final LocalDate DEFAULT_LAST_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBB";

    private static final LocalDate DEFAULT_START_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_TIME = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    @Inject
    private SteelCageRepository steelCageRepository;

    @Inject
    private SteelCageSearchRepository steelCageSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSteelCageMockMvc;

    private SteelCage steelCage;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SteelCageResource steelCageResource = new SteelCageResource();
        ReflectionTestUtils.setField(steelCageResource, "steelCageSearchRepository", steelCageSearchRepository);
        ReflectionTestUtils.setField(steelCageResource, "steelCageRepository", steelCageRepository);
        this.restSteelCageMockMvc = MockMvcBuilders.standaloneSetup(steelCageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        steelCage = new SteelCage();
        steelCage.setCreatedAt(DEFAULT_CREATED_AT);
        steelCage.setIdCage(DEFAULT_ID_CAGE);
        steelCage.setSonicPipesCompliance(DEFAULT_SONIC_PIPES_COMPLIANCE);
        steelCage.setWaterAndCappingSonicPipeFilling(DEFAULT_WATER_AND_CAPPING_SONIC_PIPE_FILLING);
        steelCage.setOverlappingCompliance(DEFAULT_OVERLAPPING_COMPLIANCE);
        steelCage.setSpacerPositionCompliance(DEFAULT_SPACER_POSITION_COMPLIANCE);
        steelCage.setDistanceBetweenCageTopAndGuideWallEdge(DEFAULT_DISTANCE_BETWEEN_CAGE_TOP_AND_GUIDE_WALL_EDGE);
        steelCage.setVerticalityCompliance(DEFAULT_VERTICALITY_COMPLIANCE);
        steelCage.setLastUpdatedAt(DEFAULT_LAST_UPDATED_AT);
        steelCage.setLastUpdatedBy(DEFAULT_LAST_UPDATED_BY);
        steelCage.setStartTime(DEFAULT_START_TIME);
        steelCage.setEndTime(DEFAULT_END_TIME);
        steelCage.setComment(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createSteelCage() throws Exception {
        int databaseSizeBeforeCreate = steelCageRepository.findAll().size();

        // Create the SteelCage

        restSteelCageMockMvc.perform(post("/api/steelCages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(steelCage)))
                .andExpect(status().isCreated());

        // Validate the SteelCage in the database
        List<SteelCage> steelCages = steelCageRepository.findAll();
        assertThat(steelCages).hasSize(databaseSizeBeforeCreate + 1);
        SteelCage testSteelCage = steelCages.get(steelCages.size() - 1);
        assertThat(testSteelCage.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testSteelCage.getIdCage()).isEqualTo(DEFAULT_ID_CAGE);
        assertThat(testSteelCage.getSonicPipesCompliance()).isEqualTo(DEFAULT_SONIC_PIPES_COMPLIANCE);
        assertThat(testSteelCage.getWaterAndCappingSonicPipeFilling()).isEqualTo(DEFAULT_WATER_AND_CAPPING_SONIC_PIPE_FILLING);
        assertThat(testSteelCage.getOverlappingCompliance()).isEqualTo(DEFAULT_OVERLAPPING_COMPLIANCE);
        assertThat(testSteelCage.getSpacerPositionCompliance()).isEqualTo(DEFAULT_SPACER_POSITION_COMPLIANCE);
        assertThat(testSteelCage.getDistanceBetweenCageTopAndGuideWallEdge()).isEqualTo(DEFAULT_DISTANCE_BETWEEN_CAGE_TOP_AND_GUIDE_WALL_EDGE);
        assertThat(testSteelCage.getVerticalityCompliance()).isEqualTo(DEFAULT_VERTICALITY_COMPLIANCE);
        assertThat(testSteelCage.getLastUpdatedAt()).isEqualTo(DEFAULT_LAST_UPDATED_AT);
        assertThat(testSteelCage.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
        assertThat(testSteelCage.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testSteelCage.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testSteelCage.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void getAllSteelCages() throws Exception {
        // Initialize the database
        steelCageRepository.saveAndFlush(steelCage);

        // Get all the steelCages
        restSteelCageMockMvc.perform(get("/api/steelCages?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(steelCage.getId().intValue())))
                .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
                .andExpect(jsonPath("$.[*].idCage").value(hasItem(DEFAULT_ID_CAGE.toString())))
                .andExpect(jsonPath("$.[*].sonicPipesCompliance").value(hasItem(DEFAULT_SONIC_PIPES_COMPLIANCE.booleanValue())))
                .andExpect(jsonPath("$.[*].waterAndCappingSonicPipeFilling").value(hasItem(DEFAULT_WATER_AND_CAPPING_SONIC_PIPE_FILLING.booleanValue())))
                .andExpect(jsonPath("$.[*].overlappingCompliance").value(hasItem(DEFAULT_OVERLAPPING_COMPLIANCE.booleanValue())))
                .andExpect(jsonPath("$.[*].spacerPositionCompliance").value(hasItem(DEFAULT_SPACER_POSITION_COMPLIANCE.booleanValue())))
                .andExpect(jsonPath("$.[*].distanceBetweenCageTopAndGuideWallEdge").value(hasItem(DEFAULT_DISTANCE_BETWEEN_CAGE_TOP_AND_GUIDE_WALL_EDGE.booleanValue())))
                .andExpect(jsonPath("$.[*].verticalityCompliance").value(hasItem(DEFAULT_VERTICALITY_COMPLIANCE.booleanValue())))
                .andExpect(jsonPath("$.[*].lastUpdatedAt").value(hasItem(DEFAULT_LAST_UPDATED_AT.toString())))
                .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY.toString())))
                .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
                .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getSteelCage() throws Exception {
        // Initialize the database
        steelCageRepository.saveAndFlush(steelCage);

        // Get the steelCage
        restSteelCageMockMvc.perform(get("/api/steelCages/{id}", steelCage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(steelCage.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.idCage").value(DEFAULT_ID_CAGE.toString()))
            .andExpect(jsonPath("$.sonicPipesCompliance").value(DEFAULT_SONIC_PIPES_COMPLIANCE.booleanValue()))
            .andExpect(jsonPath("$.waterAndCappingSonicPipeFilling").value(DEFAULT_WATER_AND_CAPPING_SONIC_PIPE_FILLING.booleanValue()))
            .andExpect(jsonPath("$.overlappingCompliance").value(DEFAULT_OVERLAPPING_COMPLIANCE.booleanValue()))
            .andExpect(jsonPath("$.spacerPositionCompliance").value(DEFAULT_SPACER_POSITION_COMPLIANCE.booleanValue()))
            .andExpect(jsonPath("$.distanceBetweenCageTopAndGuideWallEdge").value(DEFAULT_DISTANCE_BETWEEN_CAGE_TOP_AND_GUIDE_WALL_EDGE.booleanValue()))
            .andExpect(jsonPath("$.verticalityCompliance").value(DEFAULT_VERTICALITY_COMPLIANCE.booleanValue()))
            .andExpect(jsonPath("$.lastUpdatedAt").value(DEFAULT_LAST_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSteelCage() throws Exception {
        // Get the steelCage
        restSteelCageMockMvc.perform(get("/api/steelCages/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSteelCage() throws Exception {
        // Initialize the database
        steelCageRepository.saveAndFlush(steelCage);

		int databaseSizeBeforeUpdate = steelCageRepository.findAll().size();

        // Update the steelCage
        steelCage.setCreatedAt(UPDATED_CREATED_AT);
        steelCage.setIdCage(UPDATED_ID_CAGE);
        steelCage.setSonicPipesCompliance(UPDATED_SONIC_PIPES_COMPLIANCE);
        steelCage.setWaterAndCappingSonicPipeFilling(UPDATED_WATER_AND_CAPPING_SONIC_PIPE_FILLING);
        steelCage.setOverlappingCompliance(UPDATED_OVERLAPPING_COMPLIANCE);
        steelCage.setSpacerPositionCompliance(UPDATED_SPACER_POSITION_COMPLIANCE);
        steelCage.setDistanceBetweenCageTopAndGuideWallEdge(UPDATED_DISTANCE_BETWEEN_CAGE_TOP_AND_GUIDE_WALL_EDGE);
        steelCage.setVerticalityCompliance(UPDATED_VERTICALITY_COMPLIANCE);
        steelCage.setLastUpdatedAt(UPDATED_LAST_UPDATED_AT);
        steelCage.setLastUpdatedBy(UPDATED_LAST_UPDATED_BY);
        steelCage.setStartTime(UPDATED_START_TIME);
        steelCage.setEndTime(UPDATED_END_TIME);
        steelCage.setComment(UPDATED_COMMENT);

        restSteelCageMockMvc.perform(put("/api/steelCages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(steelCage)))
                .andExpect(status().isOk());

        // Validate the SteelCage in the database
        List<SteelCage> steelCages = steelCageRepository.findAll();
        assertThat(steelCages).hasSize(databaseSizeBeforeUpdate);
        SteelCage testSteelCage = steelCages.get(steelCages.size() - 1);
        assertThat(testSteelCage.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testSteelCage.getIdCage()).isEqualTo(UPDATED_ID_CAGE);
        assertThat(testSteelCage.getSonicPipesCompliance()).isEqualTo(UPDATED_SONIC_PIPES_COMPLIANCE);
        assertThat(testSteelCage.getWaterAndCappingSonicPipeFilling()).isEqualTo(UPDATED_WATER_AND_CAPPING_SONIC_PIPE_FILLING);
        assertThat(testSteelCage.getOverlappingCompliance()).isEqualTo(UPDATED_OVERLAPPING_COMPLIANCE);
        assertThat(testSteelCage.getSpacerPositionCompliance()).isEqualTo(UPDATED_SPACER_POSITION_COMPLIANCE);
        assertThat(testSteelCage.getDistanceBetweenCageTopAndGuideWallEdge()).isEqualTo(UPDATED_DISTANCE_BETWEEN_CAGE_TOP_AND_GUIDE_WALL_EDGE);
        assertThat(testSteelCage.getVerticalityCompliance()).isEqualTo(UPDATED_VERTICALITY_COMPLIANCE);
        assertThat(testSteelCage.getLastUpdatedAt()).isEqualTo(UPDATED_LAST_UPDATED_AT);
        assertThat(testSteelCage.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
        assertThat(testSteelCage.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testSteelCage.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testSteelCage.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void deleteSteelCage() throws Exception {
        // Initialize the database
        steelCageRepository.saveAndFlush(steelCage);

		int databaseSizeBeforeDelete = steelCageRepository.findAll().size();

        // Get the steelCage
        restSteelCageMockMvc.perform(delete("/api/steelCages/{id}", steelCage.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SteelCage> steelCages = steelCageRepository.findAll();
        assertThat(steelCages).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package dk.optimize.web.rest;

import dk.optimize.Application;
import dk.optimize.domain.Pile;
import dk.optimize.repository.PileRepository;
import dk.optimize.repository.search.PileSearchRepository;

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
 * Test class for the PileResource REST controller.
 *
 * @see PileResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PileResourceIntTest {


    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBB";

    private static final Long DEFAULT_NEXT_PILE = 1L;
    private static final Long UPDATED_NEXT_PILE = 2L;

    private static final Long DEFAULT_PREV_PILE = 1L;
    private static final Long UPDATED_PREV_PILE = 2L;

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    @Inject
    private PileRepository pileRepository;

    @Inject
    private PileSearchRepository pileSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPileMockMvc;

    private Pile pile;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PileResource pileResource = new PileResource();
        ReflectionTestUtils.setField(pileResource, "pileSearchRepository", pileSearchRepository);
        ReflectionTestUtils.setField(pileResource, "pileRepository", pileRepository);
        this.restPileMockMvc = MockMvcBuilders.standaloneSetup(pileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pile = new Pile();
        pile.setCreatedAt(DEFAULT_CREATED_AT);
        pile.setLastUpdatedAt(DEFAULT_LAST_UPDATED_AT);
        pile.setLastUpdatedBy(DEFAULT_LAST_UPDATED_BY);
        pile.setNextPile(DEFAULT_NEXT_PILE);
        pile.setPrevPile(DEFAULT_PREV_PILE);
        pile.setNumber(DEFAULT_NUMBER);
        pile.setComment(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createPile() throws Exception {
        int databaseSizeBeforeCreate = pileRepository.findAll().size();

        // Create the Pile

        restPileMockMvc.perform(post("/api/piles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pile)))
                .andExpect(status().isCreated());

        // Validate the Pile in the database
        List<Pile> piles = pileRepository.findAll();
        assertThat(piles).hasSize(databaseSizeBeforeCreate + 1);
        Pile testPile = piles.get(piles.size() - 1);
        assertThat(testPile.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPile.getLastUpdatedAt()).isEqualTo(DEFAULT_LAST_UPDATED_AT);
        assertThat(testPile.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
        assertThat(testPile.getNextPile()).isEqualTo(DEFAULT_NEXT_PILE);
        assertThat(testPile.getPrevPile()).isEqualTo(DEFAULT_PREV_PILE);
        assertThat(testPile.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testPile.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void getAllPiles() throws Exception {
        // Initialize the database
        pileRepository.saveAndFlush(pile);

        // Get all the piles
        restPileMockMvc.perform(get("/api/piles?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pile.getId().intValue())))
                .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
                .andExpect(jsonPath("$.[*].lastUpdatedAt").value(hasItem(DEFAULT_LAST_UPDATED_AT.toString())))
                .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY.toString())))
                .andExpect(jsonPath("$.[*].nextPile").value(hasItem(DEFAULT_NEXT_PILE.intValue())))
                .andExpect(jsonPath("$.[*].prevPile").value(hasItem(DEFAULT_PREV_PILE.intValue())))
                .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getPile() throws Exception {
        // Initialize the database
        pileRepository.saveAndFlush(pile);

        // Get the pile
        restPileMockMvc.perform(get("/api/piles/{id}", pile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pile.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.lastUpdatedAt").value(DEFAULT_LAST_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.nextPile").value(DEFAULT_NEXT_PILE.intValue()))
            .andExpect(jsonPath("$.prevPile").value(DEFAULT_PREV_PILE.intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPile() throws Exception {
        // Get the pile
        restPileMockMvc.perform(get("/api/piles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePile() throws Exception {
        // Initialize the database
        pileRepository.saveAndFlush(pile);

		int databaseSizeBeforeUpdate = pileRepository.findAll().size();

        // Update the pile
        pile.setCreatedAt(UPDATED_CREATED_AT);
        pile.setLastUpdatedAt(UPDATED_LAST_UPDATED_AT);
        pile.setLastUpdatedBy(UPDATED_LAST_UPDATED_BY);
        pile.setNextPile(UPDATED_NEXT_PILE);
        pile.setPrevPile(UPDATED_PREV_PILE);
        pile.setNumber(UPDATED_NUMBER);
        pile.setComment(UPDATED_COMMENT);

        restPileMockMvc.perform(put("/api/piles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pile)))
                .andExpect(status().isOk());

        // Validate the Pile in the database
        List<Pile> piles = pileRepository.findAll();
        assertThat(piles).hasSize(databaseSizeBeforeUpdate);
        Pile testPile = piles.get(piles.size() - 1);
        assertThat(testPile.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPile.getLastUpdatedAt()).isEqualTo(UPDATED_LAST_UPDATED_AT);
        assertThat(testPile.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
        assertThat(testPile.getNextPile()).isEqualTo(UPDATED_NEXT_PILE);
        assertThat(testPile.getPrevPile()).isEqualTo(UPDATED_PREV_PILE);
        assertThat(testPile.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testPile.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void deletePile() throws Exception {
        // Initialize the database
        pileRepository.saveAndFlush(pile);

		int databaseSizeBeforeDelete = pileRepository.findAll().size();

        // Get the pile
        restPileMockMvc.perform(delete("/api/piles/{id}", pile.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Pile> piles = pileRepository.findAll();
        assertThat(piles).hasSize(databaseSizeBeforeDelete - 1);
    }
}

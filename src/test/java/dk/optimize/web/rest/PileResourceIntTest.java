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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.math.BigDecimal;
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

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_MIX_DESIGN = "AAAAA";
    private static final String UPDATED_MIX_DESIGN = "BBBBB";

    private static final Integer DEFAULT_SLUMP_FLOW_TEST = 1;
    private static final Integer UPDATED_SLUMP_FLOW_TEST = 2;

    private static final BigDecimal DEFAULT_POURING_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_POURING_RATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_CASTED_VOLUME = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_CASTED_VOLUME = new BigDecimal(2);

    private static final BigDecimal DEFAULT_THEORETICAL_CONCRETE_VOLUME = new BigDecimal(1);
    private static final BigDecimal UPDATED_THEORETICAL_CONCRETE_VOLUME = new BigDecimal(2);

    private static final Integer DEFAULT_OVERCONSUMPTION_OF_CONCRETE = 1;
    private static final Integer UPDATED_OVERCONSUMPTION_OF_CONCRETE = 2;
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    private static final ZonedDateTime DEFAULT_SIGNATURE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_SIGNATURE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_SIGNATURE_DATE_STR = dateTimeFormatter.format(DEFAULT_SIGNATURE_DATE);
    private static final String DEFAULT_SUB_CONTRACTOR = "AAAAA";
    private static final String UPDATED_SUB_CONTRACTOR = "BBBBB";
    private static final String DEFAULT_MAIN_CONTRACTOR = "AAAAA";
    private static final String UPDATED_MAIN_CONTRACTOR = "BBBBB";
    private static final String DEFAULT_CLIENT = "AAAAA";
    private static final String UPDATED_CLIENT = "BBBBB";

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
//        PileResource pileResource = new PileResource();
//        ReflectionTestUtils.setField(pileResource, "pileSearchRepository", pileSearchRepository);
//        ReflectionTestUtils.setField(pileResource, "pileRepository", pileRepository);
//        this.restPileMockMvc = MockMvcBuilders.standaloneSetup(pileResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pile = new Pile();
        pile.setMixDesign(DEFAULT_MIX_DESIGN);
        pile.setSlumpFlowTest(DEFAULT_SLUMP_FLOW_TEST);
        pile.setPouringRate(DEFAULT_POURING_RATE);
        pile.setTotalCastedVolume(DEFAULT_TOTAL_CASTED_VOLUME);
        pile.setTheoreticalConcreteVolume(DEFAULT_THEORETICAL_CONCRETE_VOLUME);
        pile.setOverconsumptionOfConcrete(DEFAULT_OVERCONSUMPTION_OF_CONCRETE);
        pile.setComment(DEFAULT_COMMENT);
        pile.setSignatureDate(DEFAULT_SIGNATURE_DATE);
        pile.setSubContractor(DEFAULT_SUB_CONTRACTOR);
        pile.setMainContractor(DEFAULT_MAIN_CONTRACTOR);
        pile.setClient(DEFAULT_CLIENT);
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
        assertThat(testPile.getMixDesign()).isEqualTo(DEFAULT_MIX_DESIGN);
        assertThat(testPile.getSlumpFlowTest()).isEqualTo(DEFAULT_SLUMP_FLOW_TEST);
        assertThat(testPile.getPouringRate()).isEqualTo(DEFAULT_POURING_RATE);
        assertThat(testPile.getTotalCastedVolume()).isEqualTo(DEFAULT_TOTAL_CASTED_VOLUME);
        assertThat(testPile.getTheoreticalConcreteVolume()).isEqualTo(DEFAULT_THEORETICAL_CONCRETE_VOLUME);
        assertThat(testPile.getOverconsumptionOfConcrete()).isEqualTo(DEFAULT_OVERCONSUMPTION_OF_CONCRETE);
        assertThat(testPile.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testPile.getSignatureDate()).isEqualTo(DEFAULT_SIGNATURE_DATE);
        assertThat(testPile.getSubContractor()).isEqualTo(DEFAULT_SUB_CONTRACTOR);
        assertThat(testPile.getMainContractor()).isEqualTo(DEFAULT_MAIN_CONTRACTOR);
        assertThat(testPile.getClient()).isEqualTo(DEFAULT_CLIENT);
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
                .andExpect(jsonPath("$.[*].mixDesign").value(hasItem(DEFAULT_MIX_DESIGN.toString())))
                .andExpect(jsonPath("$.[*].slumpFlowTest").value(hasItem(DEFAULT_SLUMP_FLOW_TEST)))
                .andExpect(jsonPath("$.[*].pouringRate").value(hasItem(DEFAULT_POURING_RATE.intValue())))
                .andExpect(jsonPath("$.[*].totalCastedVolume").value(hasItem(DEFAULT_TOTAL_CASTED_VOLUME.intValue())))
                .andExpect(jsonPath("$.[*].theoreticalConcreteVolume").value(hasItem(DEFAULT_THEORETICAL_CONCRETE_VOLUME.intValue())))
                .andExpect(jsonPath("$.[*].overconsumptionOfConcrete").value(hasItem(DEFAULT_OVERCONSUMPTION_OF_CONCRETE)))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
                .andExpect(jsonPath("$.[*].signatureDate").value(hasItem(DEFAULT_SIGNATURE_DATE_STR)))
                .andExpect(jsonPath("$.[*].subContractor").value(hasItem(DEFAULT_SUB_CONTRACTOR.toString())))
                .andExpect(jsonPath("$.[*].mainContractor").value(hasItem(DEFAULT_MAIN_CONTRACTOR.toString())))
                .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT.toString())));
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
            .andExpect(jsonPath("$.mixDesign").value(DEFAULT_MIX_DESIGN.toString()))
            .andExpect(jsonPath("$.slumpFlowTest").value(DEFAULT_SLUMP_FLOW_TEST))
            .andExpect(jsonPath("$.pouringRate").value(DEFAULT_POURING_RATE.intValue()))
            .andExpect(jsonPath("$.totalCastedVolume").value(DEFAULT_TOTAL_CASTED_VOLUME.intValue()))
            .andExpect(jsonPath("$.theoreticalConcreteVolume").value(DEFAULT_THEORETICAL_CONCRETE_VOLUME.intValue()))
            .andExpect(jsonPath("$.overconsumptionOfConcrete").value(DEFAULT_OVERCONSUMPTION_OF_CONCRETE))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.signatureDate").value(DEFAULT_SIGNATURE_DATE_STR))
            .andExpect(jsonPath("$.subContractor").value(DEFAULT_SUB_CONTRACTOR.toString()))
            .andExpect(jsonPath("$.mainContractor").value(DEFAULT_MAIN_CONTRACTOR.toString()))
            .andExpect(jsonPath("$.client").value(DEFAULT_CLIENT.toString()));
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
        pile.setMixDesign(UPDATED_MIX_DESIGN);
        pile.setSlumpFlowTest(UPDATED_SLUMP_FLOW_TEST);
        pile.setPouringRate(UPDATED_POURING_RATE);
        pile.setTotalCastedVolume(UPDATED_TOTAL_CASTED_VOLUME);
        pile.setTheoreticalConcreteVolume(UPDATED_THEORETICAL_CONCRETE_VOLUME);
        pile.setOverconsumptionOfConcrete(UPDATED_OVERCONSUMPTION_OF_CONCRETE);
        pile.setComment(UPDATED_COMMENT);
        pile.setSignatureDate(UPDATED_SIGNATURE_DATE);
        pile.setSubContractor(UPDATED_SUB_CONTRACTOR);
        pile.setMainContractor(UPDATED_MAIN_CONTRACTOR);
        pile.setClient(UPDATED_CLIENT);

        restPileMockMvc.perform(put("/api/piles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pile)))
                .andExpect(status().isOk());

        // Validate the Pile in the database
        List<Pile> piles = pileRepository.findAll();
        assertThat(piles).hasSize(databaseSizeBeforeUpdate);
        Pile testPile = piles.get(piles.size() - 1);
        assertThat(testPile.getMixDesign()).isEqualTo(UPDATED_MIX_DESIGN);
        assertThat(testPile.getSlumpFlowTest()).isEqualTo(UPDATED_SLUMP_FLOW_TEST);
        assertThat(testPile.getPouringRate()).isEqualTo(UPDATED_POURING_RATE);
        assertThat(testPile.getTotalCastedVolume()).isEqualTo(UPDATED_TOTAL_CASTED_VOLUME);
        assertThat(testPile.getTheoreticalConcreteVolume()).isEqualTo(UPDATED_THEORETICAL_CONCRETE_VOLUME);
        assertThat(testPile.getOverconsumptionOfConcrete()).isEqualTo(UPDATED_OVERCONSUMPTION_OF_CONCRETE);
        assertThat(testPile.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testPile.getSignatureDate()).isEqualTo(UPDATED_SIGNATURE_DATE);
        assertThat(testPile.getSubContractor()).isEqualTo(UPDATED_SUB_CONTRACTOR);
        assertThat(testPile.getMainContractor()).isEqualTo(UPDATED_MAIN_CONTRACTOR);
        assertThat(testPile.getClient()).isEqualTo(UPDATED_CLIENT);
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

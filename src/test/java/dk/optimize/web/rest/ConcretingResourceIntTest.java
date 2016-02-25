package dk.optimize.web.rest;

import dk.optimize.Application;
import dk.optimize.domain.Concreting;
import dk.optimize.repository.ConcretingRepository;
import dk.optimize.repository.search.ConcretingSearchRepository;

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
 * Test class for the ConcretingResource REST controller.
 *
 * @see ConcretingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ConcretingResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBB";
    private static final String DEFAULT_MIX_DESIGN = "AAAAA";
    private static final String UPDATED_MIX_DESIGN = "BBBBB";

    private static final Integer DEFAULT_SLUMP_FLOW_TEST = 1;
    private static final Integer UPDATED_SLUMP_FLOW_TEST = 2;

    private static final BigDecimal DEFAULT_POURING_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_POURING_RATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_CASTED_VOLUME = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_CASTED_VOLUME = new BigDecimal(2);

    private static final Integer DEFAULT_OVER_CONSUMPTION = 1;
    private static final Integer UPDATED_OVER_CONSUMPTION = 2;
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_TIME_STR = dateTimeFormatter.format(DEFAULT_START_TIME);

    private static final LocalDate DEFAULT_END_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_TIME = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CALCULATED_CUMULATIVE_CLS = "AAAAA";
    private static final String UPDATED_CALCULATED_CUMULATIVE_CLS = "BBBBB";
    private static final String DEFAULT_CALCULATED_THEORETIC_CLS = "AAAAA";
    private static final String UPDATED_CALCULATED_THEORETIC_CLS = "BBBBB";
    private static final String DEFAULT_CALCULATED_DIFFERENCE = "AAAAA";
    private static final String UPDATED_CALCULATED_DIFFERENCE = "BBBBB";
    private static final String DEFAULT_CALCULATED_PERCENT = "AAAAA";
    private static final String UPDATED_CALCULATED_PERCENT = "BBBBB";

    private static final BigDecimal DEFAULT_SENT_BACK = new BigDecimal(1);
    private static final BigDecimal UPDATED_SENT_BACK = new BigDecimal(2);

    @Inject
    private ConcretingRepository concretingRepository;

    @Inject
    private ConcretingSearchRepository concretingSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restConcretingMockMvc;

    private Concreting concreting;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConcretingResource concretingResource = new ConcretingResource();
        ReflectionTestUtils.setField(concretingResource, "concretingSearchRepository", concretingSearchRepository);
        ReflectionTestUtils.setField(concretingResource, "concretingRepository", concretingRepository);
        this.restConcretingMockMvc = MockMvcBuilders.standaloneSetup(concretingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        concreting = new Concreting();
        concreting.setCreatedAt(DEFAULT_CREATED_AT);
        concreting.setLastUpdatedAt(DEFAULT_LAST_UPDATED_AT);
        concreting.setLastUpdatedBy(DEFAULT_LAST_UPDATED_BY);
        concreting.setMixDesign(DEFAULT_MIX_DESIGN);
        concreting.setSlumpFlowTest(DEFAULT_SLUMP_FLOW_TEST);
        concreting.setPouringRate(DEFAULT_POURING_RATE);
        concreting.setTotalCastedVolume(DEFAULT_TOTAL_CASTED_VOLUME);
        concreting.setOverConsumption(DEFAULT_OVER_CONSUMPTION);
        concreting.setComment(DEFAULT_COMMENT);
        concreting.setStartDate(DEFAULT_START_DATE);
        concreting.setEndDate(DEFAULT_END_DATE);
        concreting.setStartTime(DEFAULT_START_TIME);
        concreting.setEndTime(DEFAULT_END_TIME);
        concreting.setCalculatedCumulativeCls(DEFAULT_CALCULATED_CUMULATIVE_CLS);
        concreting.setCalculatedTheoreticCls(DEFAULT_CALCULATED_THEORETIC_CLS);
        concreting.setCalculatedDifference(DEFAULT_CALCULATED_DIFFERENCE);
        concreting.setCalculatedPercent(DEFAULT_CALCULATED_PERCENT);
        concreting.setSentBack(DEFAULT_SENT_BACK);
    }

    @Test
    @Transactional
    public void createConcreting() throws Exception {
        int databaseSizeBeforeCreate = concretingRepository.findAll().size();

        // Create the Concreting

        restConcretingMockMvc.perform(post("/api/concretings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(concreting)))
                .andExpect(status().isCreated());

        // Validate the Concreting in the database
        List<Concreting> concretings = concretingRepository.findAll();
        assertThat(concretings).hasSize(databaseSizeBeforeCreate + 1);
        Concreting testConcreting = concretings.get(concretings.size() - 1);
        assertThat(testConcreting.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testConcreting.getLastUpdatedAt()).isEqualTo(DEFAULT_LAST_UPDATED_AT);
        assertThat(testConcreting.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
        assertThat(testConcreting.getMixDesign()).isEqualTo(DEFAULT_MIX_DESIGN);
        assertThat(testConcreting.getSlumpFlowTest()).isEqualTo(DEFAULT_SLUMP_FLOW_TEST);
        assertThat(testConcreting.getPouringRate()).isEqualTo(DEFAULT_POURING_RATE);
        assertThat(testConcreting.getTotalCastedVolume()).isEqualTo(DEFAULT_TOTAL_CASTED_VOLUME);
        assertThat(testConcreting.getOverConsumption()).isEqualTo(DEFAULT_OVER_CONSUMPTION);
        assertThat(testConcreting.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testConcreting.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testConcreting.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testConcreting.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testConcreting.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testConcreting.getCalculatedCumulativeCls()).isEqualTo(DEFAULT_CALCULATED_CUMULATIVE_CLS);
        assertThat(testConcreting.getCalculatedTheoreticCls()).isEqualTo(DEFAULT_CALCULATED_THEORETIC_CLS);
        assertThat(testConcreting.getCalculatedDifference()).isEqualTo(DEFAULT_CALCULATED_DIFFERENCE);
        assertThat(testConcreting.getCalculatedPercent()).isEqualTo(DEFAULT_CALCULATED_PERCENT);
        assertThat(testConcreting.getSentBack()).isEqualTo(DEFAULT_SENT_BACK);
    }

    @Test
    @Transactional
    public void getAllConcretings() throws Exception {
        // Initialize the database
        concretingRepository.saveAndFlush(concreting);

        // Get all the concretings
        restConcretingMockMvc.perform(get("/api/concretings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(concreting.getId().intValue())))
                .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
                .andExpect(jsonPath("$.[*].lastUpdatedAt").value(hasItem(DEFAULT_LAST_UPDATED_AT.toString())))
                .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY.toString())))
                .andExpect(jsonPath("$.[*].mixDesign").value(hasItem(DEFAULT_MIX_DESIGN.toString())))
                .andExpect(jsonPath("$.[*].slumpFlowTest").value(hasItem(DEFAULT_SLUMP_FLOW_TEST)))
                .andExpect(jsonPath("$.[*].pouringRate").value(hasItem(DEFAULT_POURING_RATE.intValue())))
                .andExpect(jsonPath("$.[*].totalCastedVolume").value(hasItem(DEFAULT_TOTAL_CASTED_VOLUME.intValue())))
                .andExpect(jsonPath("$.[*].overConsumption").value(hasItem(DEFAULT_OVER_CONSUMPTION)))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME_STR)))
                .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
                .andExpect(jsonPath("$.[*].calculatedCumulativeCls").value(hasItem(DEFAULT_CALCULATED_CUMULATIVE_CLS.toString())))
                .andExpect(jsonPath("$.[*].calculatedTheoreticCls").value(hasItem(DEFAULT_CALCULATED_THEORETIC_CLS.toString())))
                .andExpect(jsonPath("$.[*].calculatedDifference").value(hasItem(DEFAULT_CALCULATED_DIFFERENCE.toString())))
                .andExpect(jsonPath("$.[*].calculatedPercent").value(hasItem(DEFAULT_CALCULATED_PERCENT.toString())))
                .andExpect(jsonPath("$.[*].sentBack").value(hasItem(DEFAULT_SENT_BACK.intValue())));
    }

    @Test
    @Transactional
    public void getConcreting() throws Exception {
        // Initialize the database
        concretingRepository.saveAndFlush(concreting);

        // Get the concreting
        restConcretingMockMvc.perform(get("/api/concretings/{id}", concreting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(concreting.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.lastUpdatedAt").value(DEFAULT_LAST_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.mixDesign").value(DEFAULT_MIX_DESIGN.toString()))
            .andExpect(jsonPath("$.slumpFlowTest").value(DEFAULT_SLUMP_FLOW_TEST))
            .andExpect(jsonPath("$.pouringRate").value(DEFAULT_POURING_RATE.intValue()))
            .andExpect(jsonPath("$.totalCastedVolume").value(DEFAULT_TOTAL_CASTED_VOLUME.intValue()))
            .andExpect(jsonPath("$.overConsumption").value(DEFAULT_OVER_CONSUMPTION))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME_STR))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.calculatedCumulativeCls").value(DEFAULT_CALCULATED_CUMULATIVE_CLS.toString()))
            .andExpect(jsonPath("$.calculatedTheoreticCls").value(DEFAULT_CALCULATED_THEORETIC_CLS.toString()))
            .andExpect(jsonPath("$.calculatedDifference").value(DEFAULT_CALCULATED_DIFFERENCE.toString()))
            .andExpect(jsonPath("$.calculatedPercent").value(DEFAULT_CALCULATED_PERCENT.toString()))
            .andExpect(jsonPath("$.sentBack").value(DEFAULT_SENT_BACK.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingConcreting() throws Exception {
        // Get the concreting
        restConcretingMockMvc.perform(get("/api/concretings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConcreting() throws Exception {
        // Initialize the database
        concretingRepository.saveAndFlush(concreting);

		int databaseSizeBeforeUpdate = concretingRepository.findAll().size();

        // Update the concreting
        concreting.setCreatedAt(UPDATED_CREATED_AT);
        concreting.setLastUpdatedAt(UPDATED_LAST_UPDATED_AT);
        concreting.setLastUpdatedBy(UPDATED_LAST_UPDATED_BY);
        concreting.setMixDesign(UPDATED_MIX_DESIGN);
        concreting.setSlumpFlowTest(UPDATED_SLUMP_FLOW_TEST);
        concreting.setPouringRate(UPDATED_POURING_RATE);
        concreting.setTotalCastedVolume(UPDATED_TOTAL_CASTED_VOLUME);
        concreting.setOverConsumption(UPDATED_OVER_CONSUMPTION);
        concreting.setComment(UPDATED_COMMENT);
        concreting.setStartDate(UPDATED_START_DATE);
        concreting.setEndDate(UPDATED_END_DATE);
        concreting.setStartTime(UPDATED_START_TIME);
        concreting.setEndTime(UPDATED_END_TIME);
        concreting.setCalculatedCumulativeCls(UPDATED_CALCULATED_CUMULATIVE_CLS);
        concreting.setCalculatedTheoreticCls(UPDATED_CALCULATED_THEORETIC_CLS);
        concreting.setCalculatedDifference(UPDATED_CALCULATED_DIFFERENCE);
        concreting.setCalculatedPercent(UPDATED_CALCULATED_PERCENT);
        concreting.setSentBack(UPDATED_SENT_BACK);

        restConcretingMockMvc.perform(put("/api/concretings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(concreting)))
                .andExpect(status().isOk());

        // Validate the Concreting in the database
        List<Concreting> concretings = concretingRepository.findAll();
        assertThat(concretings).hasSize(databaseSizeBeforeUpdate);
        Concreting testConcreting = concretings.get(concretings.size() - 1);
        assertThat(testConcreting.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testConcreting.getLastUpdatedAt()).isEqualTo(UPDATED_LAST_UPDATED_AT);
        assertThat(testConcreting.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
        assertThat(testConcreting.getMixDesign()).isEqualTo(UPDATED_MIX_DESIGN);
        assertThat(testConcreting.getSlumpFlowTest()).isEqualTo(UPDATED_SLUMP_FLOW_TEST);
        assertThat(testConcreting.getPouringRate()).isEqualTo(UPDATED_POURING_RATE);
        assertThat(testConcreting.getTotalCastedVolume()).isEqualTo(UPDATED_TOTAL_CASTED_VOLUME);
        assertThat(testConcreting.getOverConsumption()).isEqualTo(UPDATED_OVER_CONSUMPTION);
        assertThat(testConcreting.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testConcreting.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testConcreting.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testConcreting.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testConcreting.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testConcreting.getCalculatedCumulativeCls()).isEqualTo(UPDATED_CALCULATED_CUMULATIVE_CLS);
        assertThat(testConcreting.getCalculatedTheoreticCls()).isEqualTo(UPDATED_CALCULATED_THEORETIC_CLS);
        assertThat(testConcreting.getCalculatedDifference()).isEqualTo(UPDATED_CALCULATED_DIFFERENCE);
        assertThat(testConcreting.getCalculatedPercent()).isEqualTo(UPDATED_CALCULATED_PERCENT);
        assertThat(testConcreting.getSentBack()).isEqualTo(UPDATED_SENT_BACK);
    }

    @Test
    @Transactional
    public void deleteConcreting() throws Exception {
        // Initialize the database
        concretingRepository.saveAndFlush(concreting);

		int databaseSizeBeforeDelete = concretingRepository.findAll().size();

        // Get the concreting
        restConcretingMockMvc.perform(delete("/api/concretings/{id}", concreting.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Concreting> concretings = concretingRepository.findAll();
        assertThat(concretings).hasSize(databaseSizeBeforeDelete - 1);
    }
}

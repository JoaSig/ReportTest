package dk.optimize.web.rest;

import dk.optimize.Application;
import dk.optimize.domain.PileConcreting;
import dk.optimize.repository.PileConcretingRepository;
import dk.optimize.repository.search.PileConcretingSearchRepository;

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
 * Test class for the PileConcretingResource REST controller.
 *
 * @see PileConcretingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PileConcretingResourceIntTest {

    private static final String DEFAULT_MIX_DESIGN = "AAAAA";
    private static final String UPDATED_MIX_DESIGN = "BBBBB";

    private static final Integer DEFAULT_SLUMP1 = 1;
    private static final Integer UPDATED_SLUMP1 = 2;

    private static final Integer DEFAULT_SLUMP2 = 1;
    private static final Integer UPDATED_SLUMP2 = 2;

    private static final Integer DEFAULT_SLUMP3 = 1;
    private static final Integer UPDATED_SLUMP3 = 2;

    private static final Integer DEFAULT_SLUMP4 = 1;
    private static final Integer UPDATED_SLUMP4 = 2;

    private static final Integer DEFAULT_SLUMP5 = 1;
    private static final Integer UPDATED_SLUMP5 = 2;

    private static final Integer DEFAULT_TRUCK_ID1 = 1;
    private static final Integer UPDATED_TRUCK_ID1 = 2;

    private static final Integer DEFAULT_TRUCK_ID2 = 1;
    private static final Integer UPDATED_TRUCK_ID2 = 2;

    private static final Integer DEFAULT_TRUCK_ID3 = 1;
    private static final Integer UPDATED_TRUCK_ID3 = 2;

    private static final Integer DEFAULT_TRUCK_ID4 = 1;
    private static final Integer UPDATED_TRUCK_ID4 = 2;

    private static final Integer DEFAULT_TRUCK_ID5 = 1;
    private static final Integer UPDATED_TRUCK_ID5 = 2;

    private static final BigDecimal DEFAULT_CASTED1 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CASTED1 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CASTED2 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CASTED2 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CASTED3 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CASTED3 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CASTED4 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CASTED4 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CASTED5 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CASTED5 = new BigDecimal(2);

    private static final LocalDate DEFAULT_CONCRETING_DATE_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONCRETING_DATE_START = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CONCRETING_START_TIME = "AAAAA";
    private static final String UPDATED_CONCRETING_START_TIME = "BBBBB";
    private static final String DEFAULT_CONCRETING_END_TIME = "AAAAA";
    private static final String UPDATED_CONCRETING_END_TIME = "BBBBB";
    private static final String DEFAULT_CONCRETING_ORDER_TIME1 = "AAAAA";
    private static final String UPDATED_CONCRETING_ORDER_TIME1 = "BBBBB";
    private static final String DEFAULT_CONCRETING_ARRIVAL_TIME1 = "AAAAA";
    private static final String UPDATED_CONCRETING_ARRIVAL_TIME1 = "BBBBB";
    private static final String DEFAULT_CONCRETING_ORDER_TIME2 = "AAAAA";
    private static final String UPDATED_CONCRETING_ORDER_TIME2 = "BBBBB";
    private static final String DEFAULT_CONCRETING_ARRIVAL_TIME2 = "AAAAA";
    private static final String UPDATED_CONCRETING_ARRIVAL_TIME2 = "BBBBB";
    private static final String DEFAULT_CONCRETING_ORDER_TIME3 = "AAAAA";
    private static final String UPDATED_CONCRETING_ORDER_TIME3 = "BBBBB";
    private static final String DEFAULT_CONCRETING_ARRIVAL_TIME3 = "AAAAA";
    private static final String UPDATED_CONCRETING_ARRIVAL_TIME3 = "BBBBB";
    private static final String DEFAULT_CONCRETING_ORDER_TIME4 = "AAAAA";
    private static final String UPDATED_CONCRETING_ORDER_TIME4 = "BBBBB";
    private static final String DEFAULT_CONCRETING_ARRIVAL_TIME4 = "AAAAA";
    private static final String UPDATED_CONCRETING_ARRIVAL_TIME4 = "BBBBB";
    private static final String DEFAULT_CONCRETING_ORDER_TIME5 = "AAAAA";
    private static final String UPDATED_CONCRETING_ORDER_TIME5 = "BBBBB";
    private static final String DEFAULT_CONCRETING_ARRIVAL_TIME5 = "AAAAA";
    private static final String UPDATED_CONCRETING_ARRIVAL_TIME5 = "BBBBB";
    private static final String DEFAULT_CALCULATED_CUMULATIVE_CLS = "AAAAA";
    private static final String UPDATED_CALCULATED_CUMULATIVE_CLS = "BBBBB";
    private static final String DEFAULT_CALCULATED_THEORIC_CLS = "AAAAA";
    private static final String UPDATED_CALCULATED_THEORIC_CLS = "BBBBB";
    private static final String DEFAULT_CALCULATED_DIFFERENCE = "AAAAA";
    private static final String UPDATED_CALCULATED_DIFFERENCE = "BBBBB";
    private static final String DEFAULT_CALCULATED_PROCENT = "AAAAA";
    private static final String UPDATED_CALCULATED_PROCENT = "BBBBB";

    private static final BigDecimal DEFAULT_CONCRETE_SENT_BACK = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONCRETE_SENT_BACK = new BigDecimal(2);

    @Inject
    private PileConcretingRepository pileConcretingRepository;

    @Inject
    private PileConcretingSearchRepository pileConcretingSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPileConcretingMockMvc;

    private PileConcreting pileConcreting;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PileConcretingResource pileConcretingResource = new PileConcretingResource();
        ReflectionTestUtils.setField(pileConcretingResource, "pileConcretingSearchRepository", pileConcretingSearchRepository);
        ReflectionTestUtils.setField(pileConcretingResource, "pileConcretingRepository", pileConcretingRepository);
        this.restPileConcretingMockMvc = MockMvcBuilders.standaloneSetup(pileConcretingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pileConcreting = new PileConcreting();
        pileConcreting.setMixDesign(DEFAULT_MIX_DESIGN);
        pileConcreting.setSlump1(DEFAULT_SLUMP1);
        pileConcreting.setSlump2(DEFAULT_SLUMP2);
        pileConcreting.setSlump3(DEFAULT_SLUMP3);
        pileConcreting.setSlump4(DEFAULT_SLUMP4);
        pileConcreting.setSlump5(DEFAULT_SLUMP5);
        pileConcreting.setTruckId1(DEFAULT_TRUCK_ID1);
        pileConcreting.setTruckId2(DEFAULT_TRUCK_ID2);
        pileConcreting.setTruckId3(DEFAULT_TRUCK_ID3);
        pileConcreting.setTruckId4(DEFAULT_TRUCK_ID4);
        pileConcreting.setTruckId5(DEFAULT_TRUCK_ID5);
        pileConcreting.setCasted1(DEFAULT_CASTED1);
        pileConcreting.setCasted2(DEFAULT_CASTED2);
        pileConcreting.setCasted3(DEFAULT_CASTED3);
        pileConcreting.setCasted4(DEFAULT_CASTED4);
        pileConcreting.setCasted5(DEFAULT_CASTED5);
        pileConcreting.setConcretingDateStart(DEFAULT_CONCRETING_DATE_START);
        pileConcreting.setConcretingStartTime(DEFAULT_CONCRETING_START_TIME);
        pileConcreting.setConcretingEndTime(DEFAULT_CONCRETING_END_TIME);
        pileConcreting.setConcretingOrderTime1(DEFAULT_CONCRETING_ORDER_TIME1);
        pileConcreting.setConcretingArrivalTime1(DEFAULT_CONCRETING_ARRIVAL_TIME1);
        pileConcreting.setConcretingOrderTime2(DEFAULT_CONCRETING_ORDER_TIME2);
        pileConcreting.setConcretingArrivalTime2(DEFAULT_CONCRETING_ARRIVAL_TIME2);
        pileConcreting.setConcretingOrderTime3(DEFAULT_CONCRETING_ORDER_TIME3);
        pileConcreting.setConcretingArrivalTime3(DEFAULT_CONCRETING_ARRIVAL_TIME3);
        pileConcreting.setConcretingOrderTime4(DEFAULT_CONCRETING_ORDER_TIME4);
        pileConcreting.setConcretingArrivalTime4(DEFAULT_CONCRETING_ARRIVAL_TIME4);
        pileConcreting.setConcretingOrderTime5(DEFAULT_CONCRETING_ORDER_TIME5);
        pileConcreting.setConcretingArrivalTime5(DEFAULT_CONCRETING_ARRIVAL_TIME5);
        pileConcreting.setCalculatedCumulativeCls(DEFAULT_CALCULATED_CUMULATIVE_CLS);
        pileConcreting.setCalculatedTheoricCls(DEFAULT_CALCULATED_THEORIC_CLS);
        pileConcreting.setCalculatedDifference(DEFAULT_CALCULATED_DIFFERENCE);
        pileConcreting.setCalculatedProcent(DEFAULT_CALCULATED_PROCENT);
        pileConcreting.setConcreteSentBack(DEFAULT_CONCRETE_SENT_BACK);
    }

    @Test
    @Transactional
    public void createPileConcreting() throws Exception {
        int databaseSizeBeforeCreate = pileConcretingRepository.findAll().size();

        // Create the PileConcreting

        restPileConcretingMockMvc.perform(post("/api/pileConcretings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pileConcreting)))
                .andExpect(status().isCreated());

        // Validate the PileConcreting in the database
        List<PileConcreting> pileConcretings = pileConcretingRepository.findAll();
        assertThat(pileConcretings).hasSize(databaseSizeBeforeCreate + 1);
        PileConcreting testPileConcreting = pileConcretings.get(pileConcretings.size() - 1);
        assertThat(testPileConcreting.getMixDesign()).isEqualTo(DEFAULT_MIX_DESIGN);
        assertThat(testPileConcreting.getSlump1()).isEqualTo(DEFAULT_SLUMP1);
        assertThat(testPileConcreting.getSlump2()).isEqualTo(DEFAULT_SLUMP2);
        assertThat(testPileConcreting.getSlump3()).isEqualTo(DEFAULT_SLUMP3);
        assertThat(testPileConcreting.getSlump4()).isEqualTo(DEFAULT_SLUMP4);
        assertThat(testPileConcreting.getSlump5()).isEqualTo(DEFAULT_SLUMP5);
        assertThat(testPileConcreting.getTruckId1()).isEqualTo(DEFAULT_TRUCK_ID1);
        assertThat(testPileConcreting.getTruckId2()).isEqualTo(DEFAULT_TRUCK_ID2);
        assertThat(testPileConcreting.getTruckId3()).isEqualTo(DEFAULT_TRUCK_ID3);
        assertThat(testPileConcreting.getTruckId4()).isEqualTo(DEFAULT_TRUCK_ID4);
        assertThat(testPileConcreting.getTruckId5()).isEqualTo(DEFAULT_TRUCK_ID5);
        assertThat(testPileConcreting.getCasted1()).isEqualTo(DEFAULT_CASTED1);
        assertThat(testPileConcreting.getCasted2()).isEqualTo(DEFAULT_CASTED2);
        assertThat(testPileConcreting.getCasted3()).isEqualTo(DEFAULT_CASTED3);
        assertThat(testPileConcreting.getCasted4()).isEqualTo(DEFAULT_CASTED4);
        assertThat(testPileConcreting.getCasted5()).isEqualTo(DEFAULT_CASTED5);
        assertThat(testPileConcreting.getConcretingDateStart()).isEqualTo(DEFAULT_CONCRETING_DATE_START);
        assertThat(testPileConcreting.getConcretingStartTime()).isEqualTo(DEFAULT_CONCRETING_START_TIME);
        assertThat(testPileConcreting.getConcretingEndTime()).isEqualTo(DEFAULT_CONCRETING_END_TIME);
        assertThat(testPileConcreting.getConcretingOrderTime1()).isEqualTo(DEFAULT_CONCRETING_ORDER_TIME1);
        assertThat(testPileConcreting.getConcretingArrivalTime1()).isEqualTo(DEFAULT_CONCRETING_ARRIVAL_TIME1);
        assertThat(testPileConcreting.getConcretingOrderTime2()).isEqualTo(DEFAULT_CONCRETING_ORDER_TIME2);
        assertThat(testPileConcreting.getConcretingArrivalTime2()).isEqualTo(DEFAULT_CONCRETING_ARRIVAL_TIME2);
        assertThat(testPileConcreting.getConcretingOrderTime3()).isEqualTo(DEFAULT_CONCRETING_ORDER_TIME3);
        assertThat(testPileConcreting.getConcretingArrivalTime3()).isEqualTo(DEFAULT_CONCRETING_ARRIVAL_TIME3);
        assertThat(testPileConcreting.getConcretingOrderTime4()).isEqualTo(DEFAULT_CONCRETING_ORDER_TIME4);
        assertThat(testPileConcreting.getConcretingArrivalTime4()).isEqualTo(DEFAULT_CONCRETING_ARRIVAL_TIME4);
        assertThat(testPileConcreting.getConcretingOrderTime5()).isEqualTo(DEFAULT_CONCRETING_ORDER_TIME5);
        assertThat(testPileConcreting.getConcretingArrivalTime5()).isEqualTo(DEFAULT_CONCRETING_ARRIVAL_TIME5);
        assertThat(testPileConcreting.getCalculatedCumulativeCls()).isEqualTo(DEFAULT_CALCULATED_CUMULATIVE_CLS);
        assertThat(testPileConcreting.getCalculatedTheoricCls()).isEqualTo(DEFAULT_CALCULATED_THEORIC_CLS);
        assertThat(testPileConcreting.getCalculatedDifference()).isEqualTo(DEFAULT_CALCULATED_DIFFERENCE);
        assertThat(testPileConcreting.getCalculatedProcent()).isEqualTo(DEFAULT_CALCULATED_PROCENT);
        assertThat(testPileConcreting.getConcreteSentBack()).isEqualTo(DEFAULT_CONCRETE_SENT_BACK);
    }

    @Test
    @Transactional
    public void getAllPileConcretings() throws Exception {
        // Initialize the database
        pileConcretingRepository.saveAndFlush(pileConcreting);

        // Get all the pileConcretings
        restPileConcretingMockMvc.perform(get("/api/pileConcretings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pileConcreting.getId().intValue())))
                .andExpect(jsonPath("$.[*].mixDesign").value(hasItem(DEFAULT_MIX_DESIGN.toString())))
                .andExpect(jsonPath("$.[*].slump1").value(hasItem(DEFAULT_SLUMP1)))
                .andExpect(jsonPath("$.[*].slump2").value(hasItem(DEFAULT_SLUMP2)))
                .andExpect(jsonPath("$.[*].slump3").value(hasItem(DEFAULT_SLUMP3)))
                .andExpect(jsonPath("$.[*].slump4").value(hasItem(DEFAULT_SLUMP4)))
                .andExpect(jsonPath("$.[*].slump5").value(hasItem(DEFAULT_SLUMP5)))
                .andExpect(jsonPath("$.[*].truckId1").value(hasItem(DEFAULT_TRUCK_ID1)))
                .andExpect(jsonPath("$.[*].truckId2").value(hasItem(DEFAULT_TRUCK_ID2)))
                .andExpect(jsonPath("$.[*].truckId3").value(hasItem(DEFAULT_TRUCK_ID3)))
                .andExpect(jsonPath("$.[*].truckId4").value(hasItem(DEFAULT_TRUCK_ID4)))
                .andExpect(jsonPath("$.[*].truckId5").value(hasItem(DEFAULT_TRUCK_ID5)))
                .andExpect(jsonPath("$.[*].casted1").value(hasItem(DEFAULT_CASTED1.intValue())))
                .andExpect(jsonPath("$.[*].casted2").value(hasItem(DEFAULT_CASTED2.intValue())))
                .andExpect(jsonPath("$.[*].casted3").value(hasItem(DEFAULT_CASTED3.intValue())))
                .andExpect(jsonPath("$.[*].casted4").value(hasItem(DEFAULT_CASTED4.intValue())))
                .andExpect(jsonPath("$.[*].casted5").value(hasItem(DEFAULT_CASTED5.intValue())))
                .andExpect(jsonPath("$.[*].concretingDateStart").value(hasItem(DEFAULT_CONCRETING_DATE_START.toString())))
                .andExpect(jsonPath("$.[*].concretingStartTime").value(hasItem(DEFAULT_CONCRETING_START_TIME.toString())))
                .andExpect(jsonPath("$.[*].concretingEndTime").value(hasItem(DEFAULT_CONCRETING_END_TIME.toString())))
                .andExpect(jsonPath("$.[*].concretingOrderTime1").value(hasItem(DEFAULT_CONCRETING_ORDER_TIME1.toString())))
                .andExpect(jsonPath("$.[*].concretingArrivalTime1").value(hasItem(DEFAULT_CONCRETING_ARRIVAL_TIME1.toString())))
                .andExpect(jsonPath("$.[*].concretingOrderTime2").value(hasItem(DEFAULT_CONCRETING_ORDER_TIME2.toString())))
                .andExpect(jsonPath("$.[*].concretingArrivalTime2").value(hasItem(DEFAULT_CONCRETING_ARRIVAL_TIME2.toString())))
                .andExpect(jsonPath("$.[*].concretingOrderTime3").value(hasItem(DEFAULT_CONCRETING_ORDER_TIME3.toString())))
                .andExpect(jsonPath("$.[*].concretingArrivalTime3").value(hasItem(DEFAULT_CONCRETING_ARRIVAL_TIME3.toString())))
                .andExpect(jsonPath("$.[*].concretingOrderTime4").value(hasItem(DEFAULT_CONCRETING_ORDER_TIME4.toString())))
                .andExpect(jsonPath("$.[*].concretingArrivalTime4").value(hasItem(DEFAULT_CONCRETING_ARRIVAL_TIME4.toString())))
                .andExpect(jsonPath("$.[*].concretingOrderTime5").value(hasItem(DEFAULT_CONCRETING_ORDER_TIME5.toString())))
                .andExpect(jsonPath("$.[*].concretingArrivalTime5").value(hasItem(DEFAULT_CONCRETING_ARRIVAL_TIME5.toString())))
                .andExpect(jsonPath("$.[*].calculatedCumulativeCls").value(hasItem(DEFAULT_CALCULATED_CUMULATIVE_CLS.toString())))
                .andExpect(jsonPath("$.[*].calculatedTheoricCls").value(hasItem(DEFAULT_CALCULATED_THEORIC_CLS.toString())))
                .andExpect(jsonPath("$.[*].calculatedDifference").value(hasItem(DEFAULT_CALCULATED_DIFFERENCE.toString())))
                .andExpect(jsonPath("$.[*].calculatedProcent").value(hasItem(DEFAULT_CALCULATED_PROCENT.toString())))
                .andExpect(jsonPath("$.[*].concreteSentBack").value(hasItem(DEFAULT_CONCRETE_SENT_BACK.intValue())));
    }

    @Test
    @Transactional
    public void getPileConcreting() throws Exception {
        // Initialize the database
        pileConcretingRepository.saveAndFlush(pileConcreting);

        // Get the pileConcreting
        restPileConcretingMockMvc.perform(get("/api/pileConcretings/{id}", pileConcreting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pileConcreting.getId().intValue()))
            .andExpect(jsonPath("$.mixDesign").value(DEFAULT_MIX_DESIGN.toString()))
            .andExpect(jsonPath("$.slump1").value(DEFAULT_SLUMP1))
            .andExpect(jsonPath("$.slump2").value(DEFAULT_SLUMP2))
            .andExpect(jsonPath("$.slump3").value(DEFAULT_SLUMP3))
            .andExpect(jsonPath("$.slump4").value(DEFAULT_SLUMP4))
            .andExpect(jsonPath("$.slump5").value(DEFAULT_SLUMP5))
            .andExpect(jsonPath("$.truckId1").value(DEFAULT_TRUCK_ID1))
            .andExpect(jsonPath("$.truckId2").value(DEFAULT_TRUCK_ID2))
            .andExpect(jsonPath("$.truckId3").value(DEFAULT_TRUCK_ID3))
            .andExpect(jsonPath("$.truckId4").value(DEFAULT_TRUCK_ID4))
            .andExpect(jsonPath("$.truckId5").value(DEFAULT_TRUCK_ID5))
            .andExpect(jsonPath("$.casted1").value(DEFAULT_CASTED1.intValue()))
            .andExpect(jsonPath("$.casted2").value(DEFAULT_CASTED2.intValue()))
            .andExpect(jsonPath("$.casted3").value(DEFAULT_CASTED3.intValue()))
            .andExpect(jsonPath("$.casted4").value(DEFAULT_CASTED4.intValue()))
            .andExpect(jsonPath("$.casted5").value(DEFAULT_CASTED5.intValue()))
            .andExpect(jsonPath("$.concretingDateStart").value(DEFAULT_CONCRETING_DATE_START.toString()))
            .andExpect(jsonPath("$.concretingStartTime").value(DEFAULT_CONCRETING_START_TIME.toString()))
            .andExpect(jsonPath("$.concretingEndTime").value(DEFAULT_CONCRETING_END_TIME.toString()))
            .andExpect(jsonPath("$.concretingOrderTime1").value(DEFAULT_CONCRETING_ORDER_TIME1.toString()))
            .andExpect(jsonPath("$.concretingArrivalTime1").value(DEFAULT_CONCRETING_ARRIVAL_TIME1.toString()))
            .andExpect(jsonPath("$.concretingOrderTime2").value(DEFAULT_CONCRETING_ORDER_TIME2.toString()))
            .andExpect(jsonPath("$.concretingArrivalTime2").value(DEFAULT_CONCRETING_ARRIVAL_TIME2.toString()))
            .andExpect(jsonPath("$.concretingOrderTime3").value(DEFAULT_CONCRETING_ORDER_TIME3.toString()))
            .andExpect(jsonPath("$.concretingArrivalTime3").value(DEFAULT_CONCRETING_ARRIVAL_TIME3.toString()))
            .andExpect(jsonPath("$.concretingOrderTime4").value(DEFAULT_CONCRETING_ORDER_TIME4.toString()))
            .andExpect(jsonPath("$.concretingArrivalTime4").value(DEFAULT_CONCRETING_ARRIVAL_TIME4.toString()))
            .andExpect(jsonPath("$.concretingOrderTime5").value(DEFAULT_CONCRETING_ORDER_TIME5.toString()))
            .andExpect(jsonPath("$.concretingArrivalTime5").value(DEFAULT_CONCRETING_ARRIVAL_TIME5.toString()))
            .andExpect(jsonPath("$.calculatedCumulativeCls").value(DEFAULT_CALCULATED_CUMULATIVE_CLS.toString()))
            .andExpect(jsonPath("$.calculatedTheoricCls").value(DEFAULT_CALCULATED_THEORIC_CLS.toString()))
            .andExpect(jsonPath("$.calculatedDifference").value(DEFAULT_CALCULATED_DIFFERENCE.toString()))
            .andExpect(jsonPath("$.calculatedProcent").value(DEFAULT_CALCULATED_PROCENT.toString()))
            .andExpect(jsonPath("$.concreteSentBack").value(DEFAULT_CONCRETE_SENT_BACK.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPileConcreting() throws Exception {
        // Get the pileConcreting
        restPileConcretingMockMvc.perform(get("/api/pileConcretings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePileConcreting() throws Exception {
        // Initialize the database
        pileConcretingRepository.saveAndFlush(pileConcreting);

		int databaseSizeBeforeUpdate = pileConcretingRepository.findAll().size();

        // Update the pileConcreting
        pileConcreting.setMixDesign(UPDATED_MIX_DESIGN);
        pileConcreting.setSlump1(UPDATED_SLUMP1);
        pileConcreting.setSlump2(UPDATED_SLUMP2);
        pileConcreting.setSlump3(UPDATED_SLUMP3);
        pileConcreting.setSlump4(UPDATED_SLUMP4);
        pileConcreting.setSlump5(UPDATED_SLUMP5);
        pileConcreting.setTruckId1(UPDATED_TRUCK_ID1);
        pileConcreting.setTruckId2(UPDATED_TRUCK_ID2);
        pileConcreting.setTruckId3(UPDATED_TRUCK_ID3);
        pileConcreting.setTruckId4(UPDATED_TRUCK_ID4);
        pileConcreting.setTruckId5(UPDATED_TRUCK_ID5);
        pileConcreting.setCasted1(UPDATED_CASTED1);
        pileConcreting.setCasted2(UPDATED_CASTED2);
        pileConcreting.setCasted3(UPDATED_CASTED3);
        pileConcreting.setCasted4(UPDATED_CASTED4);
        pileConcreting.setCasted5(UPDATED_CASTED5);
        pileConcreting.setConcretingDateStart(UPDATED_CONCRETING_DATE_START);
        pileConcreting.setConcretingStartTime(UPDATED_CONCRETING_START_TIME);
        pileConcreting.setConcretingEndTime(UPDATED_CONCRETING_END_TIME);
        pileConcreting.setConcretingOrderTime1(UPDATED_CONCRETING_ORDER_TIME1);
        pileConcreting.setConcretingArrivalTime1(UPDATED_CONCRETING_ARRIVAL_TIME1);
        pileConcreting.setConcretingOrderTime2(UPDATED_CONCRETING_ORDER_TIME2);
        pileConcreting.setConcretingArrivalTime2(UPDATED_CONCRETING_ARRIVAL_TIME2);
        pileConcreting.setConcretingOrderTime3(UPDATED_CONCRETING_ORDER_TIME3);
        pileConcreting.setConcretingArrivalTime3(UPDATED_CONCRETING_ARRIVAL_TIME3);
        pileConcreting.setConcretingOrderTime4(UPDATED_CONCRETING_ORDER_TIME4);
        pileConcreting.setConcretingArrivalTime4(UPDATED_CONCRETING_ARRIVAL_TIME4);
        pileConcreting.setConcretingOrderTime5(UPDATED_CONCRETING_ORDER_TIME5);
        pileConcreting.setConcretingArrivalTime5(UPDATED_CONCRETING_ARRIVAL_TIME5);
        pileConcreting.setCalculatedCumulativeCls(UPDATED_CALCULATED_CUMULATIVE_CLS);
        pileConcreting.setCalculatedTheoricCls(UPDATED_CALCULATED_THEORIC_CLS);
        pileConcreting.setCalculatedDifference(UPDATED_CALCULATED_DIFFERENCE);
        pileConcreting.setCalculatedProcent(UPDATED_CALCULATED_PROCENT);
        pileConcreting.setConcreteSentBack(UPDATED_CONCRETE_SENT_BACK);

        restPileConcretingMockMvc.perform(put("/api/pileConcretings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pileConcreting)))
                .andExpect(status().isOk());

        // Validate the PileConcreting in the database
        List<PileConcreting> pileConcretings = pileConcretingRepository.findAll();
        assertThat(pileConcretings).hasSize(databaseSizeBeforeUpdate);
        PileConcreting testPileConcreting = pileConcretings.get(pileConcretings.size() - 1);
        assertThat(testPileConcreting.getMixDesign()).isEqualTo(UPDATED_MIX_DESIGN);
        assertThat(testPileConcreting.getSlump1()).isEqualTo(UPDATED_SLUMP1);
        assertThat(testPileConcreting.getSlump2()).isEqualTo(UPDATED_SLUMP2);
        assertThat(testPileConcreting.getSlump3()).isEqualTo(UPDATED_SLUMP3);
        assertThat(testPileConcreting.getSlump4()).isEqualTo(UPDATED_SLUMP4);
        assertThat(testPileConcreting.getSlump5()).isEqualTo(UPDATED_SLUMP5);
        assertThat(testPileConcreting.getTruckId1()).isEqualTo(UPDATED_TRUCK_ID1);
        assertThat(testPileConcreting.getTruckId2()).isEqualTo(UPDATED_TRUCK_ID2);
        assertThat(testPileConcreting.getTruckId3()).isEqualTo(UPDATED_TRUCK_ID3);
        assertThat(testPileConcreting.getTruckId4()).isEqualTo(UPDATED_TRUCK_ID4);
        assertThat(testPileConcreting.getTruckId5()).isEqualTo(UPDATED_TRUCK_ID5);
        assertThat(testPileConcreting.getCasted1()).isEqualTo(UPDATED_CASTED1);
        assertThat(testPileConcreting.getCasted2()).isEqualTo(UPDATED_CASTED2);
        assertThat(testPileConcreting.getCasted3()).isEqualTo(UPDATED_CASTED3);
        assertThat(testPileConcreting.getCasted4()).isEqualTo(UPDATED_CASTED4);
        assertThat(testPileConcreting.getCasted5()).isEqualTo(UPDATED_CASTED5);
        assertThat(testPileConcreting.getConcretingDateStart()).isEqualTo(UPDATED_CONCRETING_DATE_START);
        assertThat(testPileConcreting.getConcretingStartTime()).isEqualTo(UPDATED_CONCRETING_START_TIME);
        assertThat(testPileConcreting.getConcretingEndTime()).isEqualTo(UPDATED_CONCRETING_END_TIME);
        assertThat(testPileConcreting.getConcretingOrderTime1()).isEqualTo(UPDATED_CONCRETING_ORDER_TIME1);
        assertThat(testPileConcreting.getConcretingArrivalTime1()).isEqualTo(UPDATED_CONCRETING_ARRIVAL_TIME1);
        assertThat(testPileConcreting.getConcretingOrderTime2()).isEqualTo(UPDATED_CONCRETING_ORDER_TIME2);
        assertThat(testPileConcreting.getConcretingArrivalTime2()).isEqualTo(UPDATED_CONCRETING_ARRIVAL_TIME2);
        assertThat(testPileConcreting.getConcretingOrderTime3()).isEqualTo(UPDATED_CONCRETING_ORDER_TIME3);
        assertThat(testPileConcreting.getConcretingArrivalTime3()).isEqualTo(UPDATED_CONCRETING_ARRIVAL_TIME3);
        assertThat(testPileConcreting.getConcretingOrderTime4()).isEqualTo(UPDATED_CONCRETING_ORDER_TIME4);
        assertThat(testPileConcreting.getConcretingArrivalTime4()).isEqualTo(UPDATED_CONCRETING_ARRIVAL_TIME4);
        assertThat(testPileConcreting.getConcretingOrderTime5()).isEqualTo(UPDATED_CONCRETING_ORDER_TIME5);
        assertThat(testPileConcreting.getConcretingArrivalTime5()).isEqualTo(UPDATED_CONCRETING_ARRIVAL_TIME5);
        assertThat(testPileConcreting.getCalculatedCumulativeCls()).isEqualTo(UPDATED_CALCULATED_CUMULATIVE_CLS);
        assertThat(testPileConcreting.getCalculatedTheoricCls()).isEqualTo(UPDATED_CALCULATED_THEORIC_CLS);
        assertThat(testPileConcreting.getCalculatedDifference()).isEqualTo(UPDATED_CALCULATED_DIFFERENCE);
        assertThat(testPileConcreting.getCalculatedProcent()).isEqualTo(UPDATED_CALCULATED_PROCENT);
        assertThat(testPileConcreting.getConcreteSentBack()).isEqualTo(UPDATED_CONCRETE_SENT_BACK);
    }

    @Test
    @Transactional
    public void deletePileConcreting() throws Exception {
        // Initialize the database
        pileConcretingRepository.saveAndFlush(pileConcreting);

		int databaseSizeBeforeDelete = pileConcretingRepository.findAll().size();

        // Get the pileConcreting
        restPileConcretingMockMvc.perform(delete("/api/pileConcretings/{id}", pileConcreting.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PileConcreting> pileConcretings = pileConcretingRepository.findAll();
        assertThat(pileConcretings).hasSize(databaseSizeBeforeDelete - 1);
    }
}

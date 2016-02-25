package dk.optimize.web.rest;

import dk.optimize.Application;
import dk.optimize.domain.GeneralInfo;
import dk.optimize.repository.GeneralInfoRepository;
import dk.optimize.repository.search.GeneralInfoSearchRepository;

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
 * Test class for the GeneralInfoResource REST controller.
 *
 * @see GeneralInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GeneralInfoResourceIntTest {

    private static final String DEFAULT_TREVI_REF_NR = "AAAAA";
    private static final String UPDATED_TREVI_REF_NR = "BBBBB";
    private static final String DEFAULT_PILING_RIG_TYPE = "AAAAA";
    private static final String UPDATED_PILING_RIG_TYPE = "BBBBB";

    private static final Integer DEFAULT_MACHINE_SERIAL_NR = 1;
    private static final Integer UPDATED_MACHINE_SERIAL_NR = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private GeneralInfoRepository generalInfoRepository;

    @Inject
    private GeneralInfoSearchRepository generalInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGeneralInfoMockMvc;

    private GeneralInfo generalInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GeneralInfoResource generalInfoResource = new GeneralInfoResource();
        ReflectionTestUtils.setField(generalInfoResource, "generalInfoSearchRepository", generalInfoSearchRepository);
        ReflectionTestUtils.setField(generalInfoResource, "generalInfoRepository", generalInfoRepository);
        this.restGeneralInfoMockMvc = MockMvcBuilders.standaloneSetup(generalInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        generalInfo = new GeneralInfo();
        generalInfo.setTreviRefNr(DEFAULT_TREVI_REF_NR);
        generalInfo.setPilingRigType(DEFAULT_PILING_RIG_TYPE);
        generalInfo.setMachineSerialNr(DEFAULT_MACHINE_SERIAL_NR);
        generalInfo.setDate(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createGeneralInfo() throws Exception {
        int databaseSizeBeforeCreate = generalInfoRepository.findAll().size();

        // Create the GeneralInfo

        restGeneralInfoMockMvc.perform(post("/api/generalInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(generalInfo)))
                .andExpect(status().isCreated());

        // Validate the GeneralInfo in the database
        List<GeneralInfo> generalInfos = generalInfoRepository.findAll();
        assertThat(generalInfos).hasSize(databaseSizeBeforeCreate + 1);
        GeneralInfo testGeneralInfo = generalInfos.get(generalInfos.size() - 1);
        assertThat(testGeneralInfo.getTreviRefNr()).isEqualTo(DEFAULT_TREVI_REF_NR);
        assertThat(testGeneralInfo.getPilingRigType()).isEqualTo(DEFAULT_PILING_RIG_TYPE);
        assertThat(testGeneralInfo.getMachineSerialNr()).isEqualTo(DEFAULT_MACHINE_SERIAL_NR);
        assertThat(testGeneralInfo.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void getAllGeneralInfos() throws Exception {
        // Initialize the database
        generalInfoRepository.saveAndFlush(generalInfo);

        // Get all the generalInfos
        restGeneralInfoMockMvc.perform(get("/api/generalInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(generalInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].treviRefNr").value(hasItem(DEFAULT_TREVI_REF_NR.toString())))
                .andExpect(jsonPath("$.[*].pilingRigType").value(hasItem(DEFAULT_PILING_RIG_TYPE.toString())))
                .andExpect(jsonPath("$.[*].machineSerialNr").value(hasItem(DEFAULT_MACHINE_SERIAL_NR)))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getGeneralInfo() throws Exception {
        // Initialize the database
        generalInfoRepository.saveAndFlush(generalInfo);

        // Get the generalInfo
        restGeneralInfoMockMvc.perform(get("/api/generalInfos/{id}", generalInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(generalInfo.getId().intValue()))
            .andExpect(jsonPath("$.treviRefNr").value(DEFAULT_TREVI_REF_NR.toString()))
            .andExpect(jsonPath("$.pilingRigType").value(DEFAULT_PILING_RIG_TYPE.toString()))
            .andExpect(jsonPath("$.machineSerialNr").value(DEFAULT_MACHINE_SERIAL_NR))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGeneralInfo() throws Exception {
        // Get the generalInfo
        restGeneralInfoMockMvc.perform(get("/api/generalInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeneralInfo() throws Exception {
        // Initialize the database
        generalInfoRepository.saveAndFlush(generalInfo);

		int databaseSizeBeforeUpdate = generalInfoRepository.findAll().size();

        // Update the generalInfo
        generalInfo.setTreviRefNr(UPDATED_TREVI_REF_NR);
        generalInfo.setPilingRigType(UPDATED_PILING_RIG_TYPE);
        generalInfo.setMachineSerialNr(UPDATED_MACHINE_SERIAL_NR);
        generalInfo.setDate(UPDATED_DATE);

        restGeneralInfoMockMvc.perform(put("/api/generalInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(generalInfo)))
                .andExpect(status().isOk());

        // Validate the GeneralInfo in the database
        List<GeneralInfo> generalInfos = generalInfoRepository.findAll();
        assertThat(generalInfos).hasSize(databaseSizeBeforeUpdate);
        GeneralInfo testGeneralInfo = generalInfos.get(generalInfos.size() - 1);
        assertThat(testGeneralInfo.getTreviRefNr()).isEqualTo(UPDATED_TREVI_REF_NR);
        assertThat(testGeneralInfo.getPilingRigType()).isEqualTo(UPDATED_PILING_RIG_TYPE);
        assertThat(testGeneralInfo.getMachineSerialNr()).isEqualTo(UPDATED_MACHINE_SERIAL_NR);
        assertThat(testGeneralInfo.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteGeneralInfo() throws Exception {
        // Initialize the database
        generalInfoRepository.saveAndFlush(generalInfo);

		int databaseSizeBeforeDelete = generalInfoRepository.findAll().size();

        // Get the generalInfo
        restGeneralInfoMockMvc.perform(delete("/api/generalInfos/{id}", generalInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GeneralInfo> generalInfos = generalInfoRepository.findAll();
        assertThat(generalInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}

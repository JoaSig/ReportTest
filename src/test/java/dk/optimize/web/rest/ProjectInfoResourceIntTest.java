package dk.optimize.web.rest;

import dk.optimize.Application;
import dk.optimize.domain.ProjectInfo;
import dk.optimize.repository.ProjectInfoRepository;
import dk.optimize.repository.search.ProjectInfoSearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ProjectInfoResource REST controller.
 *
 * @see ProjectInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProjectInfoResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_WIR_CODE = "AAAAA";
    private static final String UPDATED_WIR_CODE = "BBBBB";
    private static final String DEFAULT_PHASE = "AAAAA";
    private static final String UPDATED_PHASE = "BBBBB";
    private static final String DEFAULT_SITE = "AAAAA";
    private static final String UPDATED_SITE = "BBBBB";
    private static final String DEFAULT_SUB_CONTRACTOR = "AAAAA";
    private static final String UPDATED_SUB_CONTRACTOR = "BBBBB";

    private static final Integer DEFAULT_RECORD_SN = 1;
    private static final Integer UPDATED_RECORD_SN = 2;
    private static final String DEFAULT_CONTRACT_NR = "AAAAA";
    private static final String UPDATED_CONTRACT_NR = "BBBBB";

    @Inject
    private ProjectInfoRepository projectInfoRepository;

    @Inject
    private ProjectInfoSearchRepository projectInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProjectInfoMockMvc;

    private ProjectInfo projectInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProjectInfoResource projectInfoResource = new ProjectInfoResource();
        ReflectionTestUtils.setField(projectInfoResource, "projectInfoSearchRepository", projectInfoSearchRepository);
        ReflectionTestUtils.setField(projectInfoResource, "projectInfoRepository", projectInfoRepository);
        this.restProjectInfoMockMvc = MockMvcBuilders.standaloneSetup(projectInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        projectInfo = new ProjectInfo();
        projectInfo.setName(DEFAULT_NAME);
        projectInfo.setWirCode(DEFAULT_WIR_CODE);
        projectInfo.setPhase(DEFAULT_PHASE);
        projectInfo.setSite(DEFAULT_SITE);
        projectInfo.setSubContractor(DEFAULT_SUB_CONTRACTOR);
        projectInfo.setRecordSN(DEFAULT_RECORD_SN);
        projectInfo.setContractNr(DEFAULT_CONTRACT_NR);
    }

    @Test
    @Transactional
    public void createProjectInfo() throws Exception {
        int databaseSizeBeforeCreate = projectInfoRepository.findAll().size();

        // Create the ProjectInfo

        restProjectInfoMockMvc.perform(post("/api/projectInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projectInfo)))
                .andExpect(status().isCreated());

        // Validate the ProjectInfo in the database
        List<ProjectInfo> projectInfos = projectInfoRepository.findAll();
        assertThat(projectInfos).hasSize(databaseSizeBeforeCreate + 1);
        ProjectInfo testProjectInfo = projectInfos.get(projectInfos.size() - 1);
        assertThat(testProjectInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectInfo.getWirCode()).isEqualTo(DEFAULT_WIR_CODE);
        assertThat(testProjectInfo.getPhase()).isEqualTo(DEFAULT_PHASE);
        assertThat(testProjectInfo.getSite()).isEqualTo(DEFAULT_SITE);
        assertThat(testProjectInfo.getSubContractor()).isEqualTo(DEFAULT_SUB_CONTRACTOR);
        assertThat(testProjectInfo.getRecordSN()).isEqualTo(DEFAULT_RECORD_SN);
        assertThat(testProjectInfo.getContractNr()).isEqualTo(DEFAULT_CONTRACT_NR);
    }

    @Test
    @Transactional
    public void getAllProjectInfos() throws Exception {
        // Initialize the database
        projectInfoRepository.saveAndFlush(projectInfo);

        // Get all the projectInfos
        restProjectInfoMockMvc.perform(get("/api/projectInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(projectInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].wirCode").value(hasItem(DEFAULT_WIR_CODE.toString())))
                .andExpect(jsonPath("$.[*].phase").value(hasItem(DEFAULT_PHASE.toString())))
                .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE.toString())))
                .andExpect(jsonPath("$.[*].subContractor").value(hasItem(DEFAULT_SUB_CONTRACTOR.toString())))
                .andExpect(jsonPath("$.[*].recordSN").value(hasItem(DEFAULT_RECORD_SN)))
                .andExpect(jsonPath("$.[*].contractNr").value(hasItem(DEFAULT_CONTRACT_NR.toString())));
    }

    @Test
    @Transactional
    public void getProjectInfo() throws Exception {
        // Initialize the database
        projectInfoRepository.saveAndFlush(projectInfo);

        // Get the projectInfo
        restProjectInfoMockMvc.perform(get("/api/projectInfos/{id}", projectInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(projectInfo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.wirCode").value(DEFAULT_WIR_CODE.toString()))
            .andExpect(jsonPath("$.phase").value(DEFAULT_PHASE.toString()))
            .andExpect(jsonPath("$.site").value(DEFAULT_SITE.toString()))
            .andExpect(jsonPath("$.subContractor").value(DEFAULT_SUB_CONTRACTOR.toString()))
            .andExpect(jsonPath("$.recordSN").value(DEFAULT_RECORD_SN))
            .andExpect(jsonPath("$.contractNr").value(DEFAULT_CONTRACT_NR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProjectInfo() throws Exception {
        // Get the projectInfo
        restProjectInfoMockMvc.perform(get("/api/projectInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectInfo() throws Exception {
        // Initialize the database
        projectInfoRepository.saveAndFlush(projectInfo);

		int databaseSizeBeforeUpdate = projectInfoRepository.findAll().size();

        // Update the projectInfo
        projectInfo.setName(UPDATED_NAME);
        projectInfo.setWirCode(UPDATED_WIR_CODE);
        projectInfo.setPhase(UPDATED_PHASE);
        projectInfo.setSite(UPDATED_SITE);
        projectInfo.setSubContractor(UPDATED_SUB_CONTRACTOR);
        projectInfo.setRecordSN(UPDATED_RECORD_SN);
        projectInfo.setContractNr(UPDATED_CONTRACT_NR);

        restProjectInfoMockMvc.perform(put("/api/projectInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projectInfo)))
                .andExpect(status().isOk());

        // Validate the ProjectInfo in the database
        List<ProjectInfo> projectInfos = projectInfoRepository.findAll();
        assertThat(projectInfos).hasSize(databaseSizeBeforeUpdate);
        ProjectInfo testProjectInfo = projectInfos.get(projectInfos.size() - 1);
        assertThat(testProjectInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectInfo.getWirCode()).isEqualTo(UPDATED_WIR_CODE);
        assertThat(testProjectInfo.getPhase()).isEqualTo(UPDATED_PHASE);
        assertThat(testProjectInfo.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testProjectInfo.getSubContractor()).isEqualTo(UPDATED_SUB_CONTRACTOR);
        assertThat(testProjectInfo.getRecordSN()).isEqualTo(UPDATED_RECORD_SN);
        assertThat(testProjectInfo.getContractNr()).isEqualTo(UPDATED_CONTRACT_NR);
    }

    @Test
    @Transactional
    public void deleteProjectInfo() throws Exception {
        // Initialize the database
        projectInfoRepository.saveAndFlush(projectInfo);

		int databaseSizeBeforeDelete = projectInfoRepository.findAll().size();

        // Get the projectInfo
        restProjectInfoMockMvc.perform(delete("/api/projectInfos/{id}", projectInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProjectInfo> projectInfos = projectInfoRepository.findAll();
        assertThat(projectInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}

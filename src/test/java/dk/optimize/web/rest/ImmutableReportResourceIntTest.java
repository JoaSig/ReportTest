package dk.optimize.web.rest;

import dk.optimize.Application;
import dk.optimize.domain.ImmutableReport;
import dk.optimize.repository.ImmutableReportRepository;
import dk.optimize.repository.search.ImmutableReportSearchRepository;

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
 * Test class for the ImmutableReportResource REST controller.
 *
 * @see ImmutableReportResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ImmutableReportResourceIntTest {


    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBB";

    private static final LocalDate DEFAULT_SEND_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SEND_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RECEIVED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECEIVED_AT = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";
    private static final String DEFAULT_EMAILS = "AAAAA";
    private static final String UPDATED_EMAILS = "BBBBB";

    @Inject
    private ImmutableReportRepository immutableReportRepository;

    @Inject
    private ImmutableReportSearchRepository immutableReportSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restImmutableReportMockMvc;

    private ImmutableReport immutableReport;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ImmutableReportResource immutableReportResource = new ImmutableReportResource();
        ReflectionTestUtils.setField(immutableReportResource, "immutableReportSearchRepository", immutableReportSearchRepository);
        ReflectionTestUtils.setField(immutableReportResource, "immutableReportRepository", immutableReportRepository);
        this.restImmutableReportMockMvc = MockMvcBuilders.standaloneSetup(immutableReportResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        immutableReport = new ImmutableReport();
        immutableReport.setCreatedAt(DEFAULT_CREATED_AT);
        immutableReport.setLastUpdatedAt(DEFAULT_LAST_UPDATED_AT);
        immutableReport.setLastUpdatedBy(DEFAULT_LAST_UPDATED_BY);
        immutableReport.setSendAt(DEFAULT_SEND_AT);
        immutableReport.setReceivedAt(DEFAULT_RECEIVED_AT);
        immutableReport.setDescription(DEFAULT_DESCRIPTION);
        immutableReport.setComment(DEFAULT_COMMENT);
        immutableReport.setEmails(DEFAULT_EMAILS);
    }

    @Test
    @Transactional
    public void createImmutableReport() throws Exception {
        int databaseSizeBeforeCreate = immutableReportRepository.findAll().size();

        // Create the ImmutableReport

        restImmutableReportMockMvc.perform(post("/api/immutableReports")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(immutableReport)))
                .andExpect(status().isCreated());

        // Validate the ImmutableReport in the database
        List<ImmutableReport> immutableReports = immutableReportRepository.findAll();
        assertThat(immutableReports).hasSize(databaseSizeBeforeCreate + 1);
        ImmutableReport testImmutableReport = immutableReports.get(immutableReports.size() - 1);
        assertThat(testImmutableReport.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testImmutableReport.getLastUpdatedAt()).isEqualTo(DEFAULT_LAST_UPDATED_AT);
        assertThat(testImmutableReport.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
        assertThat(testImmutableReport.getSendAt()).isEqualTo(DEFAULT_SEND_AT);
        assertThat(testImmutableReport.getReceivedAt()).isEqualTo(DEFAULT_RECEIVED_AT);
        assertThat(testImmutableReport.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testImmutableReport.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testImmutableReport.getEmails()).isEqualTo(DEFAULT_EMAILS);
    }

    @Test
    @Transactional
    public void getAllImmutableReports() throws Exception {
        // Initialize the database
        immutableReportRepository.saveAndFlush(immutableReport);

        // Get all the immutableReports
        restImmutableReportMockMvc.perform(get("/api/immutableReports?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(immutableReport.getId().intValue())))
                .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
                .andExpect(jsonPath("$.[*].lastUpdatedAt").value(hasItem(DEFAULT_LAST_UPDATED_AT.toString())))
                .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY.toString())))
                .andExpect(jsonPath("$.[*].sendAt").value(hasItem(DEFAULT_SEND_AT.toString())))
                .andExpect(jsonPath("$.[*].receivedAt").value(hasItem(DEFAULT_RECEIVED_AT.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
                .andExpect(jsonPath("$.[*].emails").value(hasItem(DEFAULT_EMAILS.toString())));
    }

    @Test
    @Transactional
    public void getImmutableReport() throws Exception {
        // Initialize the database
        immutableReportRepository.saveAndFlush(immutableReport);

        // Get the immutableReport
        restImmutableReportMockMvc.perform(get("/api/immutableReports/{id}", immutableReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(immutableReport.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.lastUpdatedAt").value(DEFAULT_LAST_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.sendAt").value(DEFAULT_SEND_AT.toString()))
            .andExpect(jsonPath("$.receivedAt").value(DEFAULT_RECEIVED_AT.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.emails").value(DEFAULT_EMAILS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingImmutableReport() throws Exception {
        // Get the immutableReport
        restImmutableReportMockMvc.perform(get("/api/immutableReports/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImmutableReport() throws Exception {
        // Initialize the database
        immutableReportRepository.saveAndFlush(immutableReport);

		int databaseSizeBeforeUpdate = immutableReportRepository.findAll().size();

        // Update the immutableReport
        immutableReport.setCreatedAt(UPDATED_CREATED_AT);
        immutableReport.setLastUpdatedAt(UPDATED_LAST_UPDATED_AT);
        immutableReport.setLastUpdatedBy(UPDATED_LAST_UPDATED_BY);
        immutableReport.setSendAt(UPDATED_SEND_AT);
        immutableReport.setReceivedAt(UPDATED_RECEIVED_AT);
        immutableReport.setDescription(UPDATED_DESCRIPTION);
        immutableReport.setComment(UPDATED_COMMENT);
        immutableReport.setEmails(UPDATED_EMAILS);

        restImmutableReportMockMvc.perform(put("/api/immutableReports")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(immutableReport)))
                .andExpect(status().isOk());

        // Validate the ImmutableReport in the database
        List<ImmutableReport> immutableReports = immutableReportRepository.findAll();
        assertThat(immutableReports).hasSize(databaseSizeBeforeUpdate);
        ImmutableReport testImmutableReport = immutableReports.get(immutableReports.size() - 1);
        assertThat(testImmutableReport.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testImmutableReport.getLastUpdatedAt()).isEqualTo(UPDATED_LAST_UPDATED_AT);
        assertThat(testImmutableReport.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
        assertThat(testImmutableReport.getSendAt()).isEqualTo(UPDATED_SEND_AT);
        assertThat(testImmutableReport.getReceivedAt()).isEqualTo(UPDATED_RECEIVED_AT);
        assertThat(testImmutableReport.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testImmutableReport.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testImmutableReport.getEmails()).isEqualTo(UPDATED_EMAILS);
    }

    @Test
    @Transactional
    public void deleteImmutableReport() throws Exception {
        // Initialize the database
        immutableReportRepository.saveAndFlush(immutableReport);

		int databaseSizeBeforeDelete = immutableReportRepository.findAll().size();

        // Get the immutableReport
        restImmutableReportMockMvc.perform(delete("/api/immutableReports/{id}", immutableReport.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ImmutableReport> immutableReports = immutableReportRepository.findAll();
        assertThat(immutableReports).hasSize(databaseSizeBeforeDelete - 1);
    }
}

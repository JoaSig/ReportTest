package dk.optimize.web.rest;

import dk.optimize.Application;
import dk.optimize.domain.Signature;
import dk.optimize.repository.SignatureRepository;
import dk.optimize.repository.search.SignatureSearchRepository;

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
 * Test class for the SignatureResource REST controller.
 *
 * @see SignatureResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SignatureResourceIntTest {


    private static final LocalDate DEFAULT_SIGNED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SIGNED_AT = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private SignatureRepository signatureRepository;

    @Inject
    private SignatureSearchRepository signatureSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSignatureMockMvc;

    private Signature signature;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SignatureResource signatureResource = new SignatureResource();
        ReflectionTestUtils.setField(signatureResource, "signatureSearchRepository", signatureSearchRepository);
        ReflectionTestUtils.setField(signatureResource, "signatureRepository", signatureRepository);
        this.restSignatureMockMvc = MockMvcBuilders.standaloneSetup(signatureResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        signature = new Signature();
        signature.setSignedAt(DEFAULT_SIGNED_AT);
    }

    @Test
    @Transactional
    public void createSignature() throws Exception {
        int databaseSizeBeforeCreate = signatureRepository.findAll().size();

        // Create the Signature

        restSignatureMockMvc.perform(post("/api/signatures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(signature)))
                .andExpect(status().isCreated());

        // Validate the Signature in the database
        List<Signature> signatures = signatureRepository.findAll();
        assertThat(signatures).hasSize(databaseSizeBeforeCreate + 1);
        Signature testSignature = signatures.get(signatures.size() - 1);
        assertThat(testSignature.getSignedAt()).isEqualTo(DEFAULT_SIGNED_AT);
    }

    @Test
    @Transactional
    public void getAllSignatures() throws Exception {
        // Initialize the database
        signatureRepository.saveAndFlush(signature);

        // Get all the signatures
        restSignatureMockMvc.perform(get("/api/signatures?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(signature.getId().intValue())))
                .andExpect(jsonPath("$.[*].signedAt").value(hasItem(DEFAULT_SIGNED_AT.toString())));
    }

    @Test
    @Transactional
    public void getSignature() throws Exception {
        // Initialize the database
        signatureRepository.saveAndFlush(signature);

        // Get the signature
        restSignatureMockMvc.perform(get("/api/signatures/{id}", signature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(signature.getId().intValue()))
            .andExpect(jsonPath("$.signedAt").value(DEFAULT_SIGNED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSignature() throws Exception {
        // Get the signature
        restSignatureMockMvc.perform(get("/api/signatures/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSignature() throws Exception {
        // Initialize the database
        signatureRepository.saveAndFlush(signature);

		int databaseSizeBeforeUpdate = signatureRepository.findAll().size();

        // Update the signature
        signature.setSignedAt(UPDATED_SIGNED_AT);

        restSignatureMockMvc.perform(put("/api/signatures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(signature)))
                .andExpect(status().isOk());

        // Validate the Signature in the database
        List<Signature> signatures = signatureRepository.findAll();
        assertThat(signatures).hasSize(databaseSizeBeforeUpdate);
        Signature testSignature = signatures.get(signatures.size() - 1);
        assertThat(testSignature.getSignedAt()).isEqualTo(UPDATED_SIGNED_AT);
    }

    @Test
    @Transactional
    public void deleteSignature() throws Exception {
        // Initialize the database
        signatureRepository.saveAndFlush(signature);

		int databaseSizeBeforeDelete = signatureRepository.findAll().size();

        // Get the signature
        restSignatureMockMvc.perform(delete("/api/signatures/{id}", signature.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Signature> signatures = signatureRepository.findAll();
        assertThat(signatures).hasSize(databaseSizeBeforeDelete - 1);
    }
}

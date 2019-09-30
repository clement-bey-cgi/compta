package com.bicc.compta.web.rest;

import com.bicc.compta.ComptaApp;
import com.bicc.compta.domain.FicheDeContact;
import com.bicc.compta.repository.FicheDeContactRepository;
import com.bicc.compta.service.FicheDeContactService;
import com.bicc.compta.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.bicc.compta.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FicheDeContactResource} REST controller.
 */
@SpringBootTest(classes = ComptaApp.class)
public class FicheDeContactResourceIT {

    private static final Boolean DEFAULT_GENRE = false;
    private static final Boolean UPDATED_GENRE = true;

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_DE_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DE_TELEPHONE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_DE_NAISSANCE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private FicheDeContactRepository ficheDeContactRepository;

    @Autowired
    private FicheDeContactService ficheDeContactService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restFicheDeContactMockMvc;

    private FicheDeContact ficheDeContact;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FicheDeContactResource ficheDeContactResource = new FicheDeContactResource(ficheDeContactService);
        this.restFicheDeContactMockMvc = MockMvcBuilders.standaloneSetup(ficheDeContactResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FicheDeContact createEntity(EntityManager em) {
        FicheDeContact ficheDeContact = new FicheDeContact()
            .genre(DEFAULT_GENRE)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .titre(DEFAULT_TITRE)
            .mail(DEFAULT_MAIL)
            .numeroDeTelephone(DEFAULT_NUMERO_DE_TELEPHONE)
            .dateDeNaissance(DEFAULT_DATE_DE_NAISSANCE);
        return ficheDeContact;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FicheDeContact createUpdatedEntity(EntityManager em) {
        FicheDeContact ficheDeContact = new FicheDeContact()
            .genre(UPDATED_GENRE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .titre(UPDATED_TITRE)
            .mail(UPDATED_MAIL)
            .numeroDeTelephone(UPDATED_NUMERO_DE_TELEPHONE)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE);
        return ficheDeContact;
    }

    @BeforeEach
    public void initTest() {
        ficheDeContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createFicheDeContact() throws Exception {
        int databaseSizeBeforeCreate = ficheDeContactRepository.findAll().size();

        // Create the FicheDeContact
        restFicheDeContactMockMvc.perform(post("/api/fiche-de-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheDeContact)))
            .andExpect(status().isCreated());

        // Validate the FicheDeContact in the database
        List<FicheDeContact> ficheDeContactList = ficheDeContactRepository.findAll();
        assertThat(ficheDeContactList).hasSize(databaseSizeBeforeCreate + 1);
        FicheDeContact testFicheDeContact = ficheDeContactList.get(ficheDeContactList.size() - 1);
        assertThat(testFicheDeContact.isGenre()).isEqualTo(DEFAULT_GENRE);
        assertThat(testFicheDeContact.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testFicheDeContact.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testFicheDeContact.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testFicheDeContact.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testFicheDeContact.getNumeroDeTelephone()).isEqualTo(DEFAULT_NUMERO_DE_TELEPHONE);
        assertThat(testFicheDeContact.getDateDeNaissance()).isEqualTo(DEFAULT_DATE_DE_NAISSANCE);
    }

    @Test
    @Transactional
    public void createFicheDeContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ficheDeContactRepository.findAll().size();

        // Create the FicheDeContact with an existing ID
        ficheDeContact.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFicheDeContactMockMvc.perform(post("/api/fiche-de-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheDeContact)))
            .andExpect(status().isBadRequest());

        // Validate the FicheDeContact in the database
        List<FicheDeContact> ficheDeContactList = ficheDeContactRepository.findAll();
        assertThat(ficheDeContactList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFicheDeContacts() throws Exception {
        // Initialize the database
        ficheDeContactRepository.saveAndFlush(ficheDeContact);

        // Get all the ficheDeContactList
        restFicheDeContactMockMvc.perform(get("/api/fiche-de-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ficheDeContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].genre").value(hasItem(DEFAULT_GENRE.booleanValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL.toString())))
            .andExpect(jsonPath("$.[*].numeroDeTelephone").value(hasItem(DEFAULT_NUMERO_DE_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].dateDeNaissance").value(hasItem(DEFAULT_DATE_DE_NAISSANCE.toString())));
    }
    
    @Test
    @Transactional
    public void getFicheDeContact() throws Exception {
        // Initialize the database
        ficheDeContactRepository.saveAndFlush(ficheDeContact);

        // Get the ficheDeContact
        restFicheDeContactMockMvc.perform(get("/api/fiche-de-contacts/{id}", ficheDeContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ficheDeContact.getId().intValue()))
            .andExpect(jsonPath("$.genre").value(DEFAULT_GENRE.booleanValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL.toString()))
            .andExpect(jsonPath("$.numeroDeTelephone").value(DEFAULT_NUMERO_DE_TELEPHONE.toString()))
            .andExpect(jsonPath("$.dateDeNaissance").value(DEFAULT_DATE_DE_NAISSANCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFicheDeContact() throws Exception {
        // Get the ficheDeContact
        restFicheDeContactMockMvc.perform(get("/api/fiche-de-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFicheDeContact() throws Exception {
        // Initialize the database
        ficheDeContactService.save(ficheDeContact);

        int databaseSizeBeforeUpdate = ficheDeContactRepository.findAll().size();

        // Update the ficheDeContact
        FicheDeContact updatedFicheDeContact = ficheDeContactRepository.findById(ficheDeContact.getId()).get();
        // Disconnect from session so that the updates on updatedFicheDeContact are not directly saved in db
        em.detach(updatedFicheDeContact);
        updatedFicheDeContact
            .genre(UPDATED_GENRE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .titre(UPDATED_TITRE)
            .mail(UPDATED_MAIL)
            .numeroDeTelephone(UPDATED_NUMERO_DE_TELEPHONE)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE);

        restFicheDeContactMockMvc.perform(put("/api/fiche-de-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFicheDeContact)))
            .andExpect(status().isOk());

        // Validate the FicheDeContact in the database
        List<FicheDeContact> ficheDeContactList = ficheDeContactRepository.findAll();
        assertThat(ficheDeContactList).hasSize(databaseSizeBeforeUpdate);
        FicheDeContact testFicheDeContact = ficheDeContactList.get(ficheDeContactList.size() - 1);
        assertThat(testFicheDeContact.isGenre()).isEqualTo(UPDATED_GENRE);
        assertThat(testFicheDeContact.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testFicheDeContact.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testFicheDeContact.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testFicheDeContact.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testFicheDeContact.getNumeroDeTelephone()).isEqualTo(UPDATED_NUMERO_DE_TELEPHONE);
        assertThat(testFicheDeContact.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingFicheDeContact() throws Exception {
        int databaseSizeBeforeUpdate = ficheDeContactRepository.findAll().size();

        // Create the FicheDeContact

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFicheDeContactMockMvc.perform(put("/api/fiche-de-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheDeContact)))
            .andExpect(status().isBadRequest());

        // Validate the FicheDeContact in the database
        List<FicheDeContact> ficheDeContactList = ficheDeContactRepository.findAll();
        assertThat(ficheDeContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFicheDeContact() throws Exception {
        // Initialize the database
        ficheDeContactService.save(ficheDeContact);

        int databaseSizeBeforeDelete = ficheDeContactRepository.findAll().size();

        // Delete the ficheDeContact
        restFicheDeContactMockMvc.perform(delete("/api/fiche-de-contacts/{id}", ficheDeContact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FicheDeContact> ficheDeContactList = ficheDeContactRepository.findAll();
        assertThat(ficheDeContactList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FicheDeContact.class);
        FicheDeContact ficheDeContact1 = new FicheDeContact();
        ficheDeContact1.setId(1L);
        FicheDeContact ficheDeContact2 = new FicheDeContact();
        ficheDeContact2.setId(ficheDeContact1.getId());
        assertThat(ficheDeContact1).isEqualTo(ficheDeContact2);
        ficheDeContact2.setId(2L);
        assertThat(ficheDeContact1).isNotEqualTo(ficheDeContact2);
        ficheDeContact1.setId(null);
        assertThat(ficheDeContact1).isNotEqualTo(ficheDeContact2);
    }
}

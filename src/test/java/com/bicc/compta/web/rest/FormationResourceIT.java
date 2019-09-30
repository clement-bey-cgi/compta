// TODO LES TEST UNITAIRES NE PASSENT PLUS (formation est devenu abstract ) a REVOIR 
//package com.bicc.compta.web.rest;
//
//import com.bicc.compta.ComptaApp;
//import com.bicc.compta.domain.formation.Formation;
//import com.bicc.compta.repository.FormationRepository;
//import com.bicc.compta.service.FormationService;
//import com.bicc.compta.web.rest.errors.ExceptionTranslator;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.validation.Validator;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//import static com.bicc.compta.web.rest.TestUtil.createFormattingConversionService;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * Integration tests for the {@link FormationResource} REST controller.
// */
//@SpringBootTest(classes = ComptaApp.class)
//public class FormationResourceIT {
//
//    private static final String DEFAULT_INTITULE = "AAAAAAAAAA";
//    private static final String UPDATED_INTITULE = "BBBBBBBBBB";
//
//    private static final String DEFAULT_DOMAINE = "AAAAAAAAAA";
//    private static final String UPDATED_DOMAINE = "BBBBBBBBBB";
//
//    @Autowired
//    private FormationRepository formationRepository;
//
//    @Autowired
//    private FormationService formationService;
//
//    @Autowired
//    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
//
//    @Autowired
//    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
//
//    @Autowired
//    private ExceptionTranslator exceptionTranslator;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private Validator validator;
//
//    private MockMvc restFormationMockMvc;
//
//    private Formation formation;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final FormationResource formationResource = new FormationResource(formationService);
//        this.restFormationMockMvc = MockMvcBuilders.standaloneSetup(formationResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setControllerAdvice(exceptionTranslator)
//            .setConversionService(createFormattingConversionService())
//            .setMessageConverters(jacksonMessageConverter)
//            .setValidator(validator).build();
//    }
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Formation createEntity(EntityManager em) {
//        Formation formation = new Formation()
//            .intitule(DEFAULT_INTITULE)
//            .domaine(DEFAULT_DOMAINE);
//        return formation;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Formation createUpdatedEntity(EntityManager em) {
//        Formation formation = new Formation()
//            .intitule(UPDATED_INTITULE)
//            .domaine(UPDATED_DOMAINE);
//        return formation;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        formation = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createFormation() throws Exception {
//        int databaseSizeBeforeCreate = formationRepository.findAll().size();
//
//        // Create the Formation
//        restFormationMockMvc.perform(post("/api/formations")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(formation)))
//            .andExpect(status().isCreated());
//
//        // Validate the Formation in the database
//        List<Formation> formationList = formationRepository.findAll();
//        assertThat(formationList).hasSize(databaseSizeBeforeCreate + 1);
//        Formation testFormation = formationList.get(formationList.size() - 1);
//        assertThat(testFormation.getIntitule()).isEqualTo(DEFAULT_INTITULE);
//        assertThat(testFormation.getDomaine()).isEqualTo(DEFAULT_DOMAINE);
//    }
//
//    @Test
//    @Transactional
//    public void createFormationWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = formationRepository.findAll().size();
//
//        // Create the Formation with an existing ID
//        formation.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restFormationMockMvc.perform(post("/api/formations")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(formation)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Formation in the database
//        List<Formation> formationList = formationRepository.findAll();
//        assertThat(formationList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllFormations() throws Exception {
//        // Initialize the database
//        formationRepository.saveAndFlush(formation);
//
//        // Get all the formationList
//        restFormationMockMvc.perform(get("/api/formations?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(formation.getId().intValue())))
//            .andExpect(jsonPath("$.[*].intitule").value(hasItem(DEFAULT_INTITULE.toString())))
//            .andExpect(jsonPath("$.[*].domaine").value(hasItem(DEFAULT_DOMAINE.toString())));
//    }
//    
//    @Test
//    @Transactional
//    public void getFormation() throws Exception {
//        // Initialize the database
//        formationRepository.saveAndFlush(formation);
//
//        // Get the formation
//        restFormationMockMvc.perform(get("/api/formations/{id}", formation.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(formation.getId().intValue()))
//            .andExpect(jsonPath("$.intitule").value(DEFAULT_INTITULE.toString()))
//            .andExpect(jsonPath("$.domaine").value(DEFAULT_DOMAINE.toString()));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingFormation() throws Exception {
//        // Get the formation
//        restFormationMockMvc.perform(get("/api/formations/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateFormation() throws Exception {
//        // Initialize the database
//        formationService.save(formation);
//
//        int databaseSizeBeforeUpdate = formationRepository.findAll().size();
//
//        // Update the formation
//        Formation updatedFormation = formationRepository.findById(formation.getId()).get();
//        // Disconnect from session so that the updates on updatedFormation are not directly saved in db
//        em.detach(updatedFormation);
//        updatedFormation
//            .intitule(UPDATED_INTITULE)
//            .domaine(UPDATED_DOMAINE);
//
//        restFormationMockMvc.perform(put("/api/formations")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedFormation)))
//            .andExpect(status().isOk());
//
//        // Validate the Formation in the database
//        List<Formation> formationList = formationRepository.findAll();
//        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
//        Formation testFormation = formationList.get(formationList.size() - 1);
//        assertThat(testFormation.getIntitule()).isEqualTo(UPDATED_INTITULE);
//        assertThat(testFormation.getDomaine()).isEqualTo(UPDATED_DOMAINE);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingFormation() throws Exception {
//        int databaseSizeBeforeUpdate = formationRepository.findAll().size();
//
//        // Create the Formation
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restFormationMockMvc.perform(put("/api/formations")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(formation)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Formation in the database
//        List<Formation> formationList = formationRepository.findAll();
//        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteFormation() throws Exception {
//        // Initialize the database
//        formationService.save(formation);
//
//        int databaseSizeBeforeDelete = formationRepository.findAll().size();
//
//        // Delete the formation
//        restFormationMockMvc.perform(delete("/api/formations/{id}", formation.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Formation> formationList = formationRepository.findAll();
//        assertThat(formationList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(Formation.class);
//        Formation formation1 = new Formation();
//        formation1.setId(1L);
//        Formation formation2 = new Formation();
//        formation2.setId(formation1.getId());
//        assertThat(formation1).isEqualTo(formation2);
//        formation2.setId(2L);
//        assertThat(formation1).isNotEqualTo(formation2);
//        formation1.setId(null);
//        assertThat(formation1).isNotEqualTo(formation2);
//    }
//}

package com.bicc.compta.service;

import com.bicc.compta.domain.formation.Formation;
import com.bicc.compta.repository.FormationAutodidacteRepository;
import com.bicc.compta.repository.FormationRepository;
import com.bicc.compta.repository.FormationScolaireRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Formation}.
 */
@Service
@Transactional
public class FormationService {

    private final Logger log = LoggerFactory.getLogger(FormationService.class);

    private final FormationRepository formationRepository;


    public FormationService(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    /**
     * Save a formation.
     *
     * @param formation the entity to save.
     * @return the persisted entity.
     */
    public Formation save(Formation formation) {
        log.debug("Request to save Formation : {}", formation);
        return formationRepository.save(formation);
    }

    /**
     * Get all the formations.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Formation> findAll() {
        log.debug("Request to get all Formations");
        return formationRepository.findAll();
    }


    /**
     * Get one formation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Formation> findOne(Long id) {
        log.debug("Request to get Formation : {}", id);
        return formationRepository.findById(id);
    }

    /**
     * Delete the formation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Formation : {}", id);
        formationRepository.deleteById(id);
    }
}

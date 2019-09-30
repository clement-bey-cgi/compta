package com.bicc.compta.service;

import com.bicc.compta.domain.FicheDeContact;
import com.bicc.compta.repository.FicheDeContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link FicheDeContact}.
 */
@Service
@Transactional
public class FicheDeContactService {

    private final Logger log = LoggerFactory.getLogger(FicheDeContactService.class);

    private final FicheDeContactRepository ficheDeContactRepository;

    public FicheDeContactService(FicheDeContactRepository ficheDeContactRepository) {
        this.ficheDeContactRepository = ficheDeContactRepository;
    }

    /**
     * Save a ficheDeContact.
     *
     * @param ficheDeContact the entity to save.
     * @return the persisted entity.
     */
    public FicheDeContact save(FicheDeContact ficheDeContact) {
        log.debug("Request to save FicheDeContact : {}", ficheDeContact);
        return ficheDeContactRepository.save(ficheDeContact);
    }

    /**
     * Get all the ficheDeContacts.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FicheDeContact> findAll() {
        log.debug("Request to get all FicheDeContacts");
        return ficheDeContactRepository.findAll();
    }


    /**
     * Get one ficheDeContact by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FicheDeContact> findOne(Long id) {
        log.debug("Request to get FicheDeContact : {}", id);
        return ficheDeContactRepository.findById(id);
    }

    /**
     * Delete the ficheDeContact by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FicheDeContact : {}", id);
        ficheDeContactRepository.deleteById(id);
    }
}

package com.bicc.compta.service;

import com.bicc.compta.domain.FicheDeContact;
import com.bicc.compta.repository.FicheDeContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FicheDeContact> findAll(Pageable pageable) {
        log.debug("Request to get all FicheDeContacts");
        return ficheDeContactRepository.findAll(pageable);
    }



    /**
    *  Get all the ficheDeContacts where Cv is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<FicheDeContact> findAllWhereCvIsNull() {
        log.debug("Request to get all ficheDeContacts where Cv is null");
        return StreamSupport
            .stream(ficheDeContactRepository.findAll().spliterator(), false)
            .filter(ficheDeContact -> ficheDeContact.getCv() == null)
            .collect(Collectors.toList());
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

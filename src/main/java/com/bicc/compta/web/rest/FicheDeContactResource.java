package com.bicc.compta.web.rest;

import com.bicc.compta.domain.FicheDeContact;
import com.bicc.compta.service.FicheDeContactService;
import com.bicc.compta.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.bicc.compta.domain.FicheDeContact}.
 */
@RestController
@RequestMapping("/api")
public class FicheDeContactResource {

    private final Logger log = LoggerFactory.getLogger(FicheDeContactResource.class);

    private static final String ENTITY_NAME = "ficheDeContact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FicheDeContactService ficheDeContactService;

    public FicheDeContactResource(FicheDeContactService ficheDeContactService) {
        this.ficheDeContactService = ficheDeContactService;
    }

    /**
     * {@code POST  /fiche-de-contacts} : Create a new ficheDeContact.
     *
     * @param ficheDeContact the ficheDeContact to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ficheDeContact, or with status {@code 400 (Bad Request)} if the ficheDeContact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fiche-de-contacts")
    public ResponseEntity<FicheDeContact> createFicheDeContact(@RequestBody FicheDeContact ficheDeContact) throws URISyntaxException {
        log.debug("REST request to save FicheDeContact : {}", ficheDeContact);
        if (ficheDeContact.getId() != null) {
            throw new BadRequestAlertException("A new ficheDeContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FicheDeContact result = ficheDeContactService.save(ficheDeContact);
        return ResponseEntity.created(new URI("/api/fiche-de-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fiche-de-contacts} : Updates an existing ficheDeContact.
     *
     * @param ficheDeContact the ficheDeContact to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ficheDeContact,
     * or with status {@code 400 (Bad Request)} if the ficheDeContact is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ficheDeContact couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fiche-de-contacts")
    public ResponseEntity<FicheDeContact> updateFicheDeContact(@RequestBody FicheDeContact ficheDeContact) throws URISyntaxException {
        log.debug("REST request to update FicheDeContact : {}", ficheDeContact);
        if (ficheDeContact.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FicheDeContact result = ficheDeContactService.save(ficheDeContact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ficheDeContact.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fiche-de-contacts} : get all the ficheDeContacts.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ficheDeContacts in body.
     */
    @GetMapping("/fiche-de-contacts")
    public List<FicheDeContact> getAllFicheDeContacts() {
        log.debug("REST request to get all FicheDeContacts");
        return ficheDeContactService.findAll();
    }

    /**
     * {@code GET  /fiche-de-contacts/:id} : get the "id" ficheDeContact.
     *
     * @param id the id of the ficheDeContact to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ficheDeContact, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fiche-de-contacts/{id}")
    public ResponseEntity<FicheDeContact> getFicheDeContact(@PathVariable Long id) {
        log.debug("REST request to get FicheDeContact : {}", id);
        Optional<FicheDeContact> ficheDeContact = ficheDeContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ficheDeContact);
    }

    /**
     * {@code DELETE  /fiche-de-contacts/:id} : delete the "id" ficheDeContact.
     *
     * @param id the id of the ficheDeContact to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fiche-de-contacts/{id}")
    public ResponseEntity<Void> deleteFicheDeContact(@PathVariable Long id) {
        log.debug("REST request to delete FicheDeContact : {}", id);
        ficheDeContactService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

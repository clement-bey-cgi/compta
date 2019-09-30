package com.bicc.compta.domain.formation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Formation.
 */
@Entity
@Table(name = "formation_scolaire")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@PrimaryKeyJoinColumn(name = "id")
public class FormationScolaire extends Formation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "institut_de_formation")
    private String institutDeFormation;

    @Column(name = "niveau_de_diplome")
    private String niveauDeDiplome;

    @Column(name = "obtention_diplome")
    private LocalDate obtentionDiplome;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove
	public String getInstitutDeFormation() {
		return institutDeFormation;
	}

	public void setInstitutDeFormation(String institutDeFormation) {
		this.institutDeFormation = institutDeFormation;
	}

	public String getNiveauDeDiplome() {
		return niveauDeDiplome;
	}

	public void setNiveauDeDiplome(String niveauDeDiplome) {
		this.niveauDeDiplome = niveauDeDiplome;
	}

	public LocalDate getObtentionDiplome() {
		return obtentionDiplome;
	}

	public void setObtentionDiplome(LocalDate obtentionDiplome) {
		this.obtentionDiplome = obtentionDiplome;
	}
}

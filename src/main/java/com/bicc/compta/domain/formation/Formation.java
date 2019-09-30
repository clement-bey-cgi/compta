package com.bicc.compta.domain.formation;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bicc.compta.domain.FicheDeContact;

/**
 * A Formation.
 */
@Entity
@Table(name = "formation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Formation implements Serializable {

	/*
	 * TODO 
	 * 0 - modifier la page d'accueil : doit être le component fiche de contacts --> inclus compontent formations display all formations 
	    1- Modifier l'affichage front liste formations pour faire la différence dans l'affichage formation auto / scolaire
	    1a- verifier le tri de la liste par ordre chronologique décroissant
	    2- Modifier l'affichage de l'édit formation pour administrateur
	    3- coder le converter boolean / genre à l'affichage et pour la création / édition
	    
	    3- modifier les droits : seuls les administrateur peuvent creer modifier supprimer editer 
	    	la fiche de contact / formation
	    	
	    3- ajouter script faker / liquibase pour affilier les formations existantes H2 a auto / scolaire (5/5)
	    
		5- tester les save / edit / suppr pour les deux types de formation avec le repo formation général : ca 
			devrait marcher mais il faut vérifier le comportement	    
		4- Supprimer les repository formation auto / sco --> plus besoin formation gère les deux si etape avant marche

		6- creer l'entity projet, Projet, ProjetPersonnel ProjetProfessionnel et passer à l'écran PROJET
			- Problématique de
	*/
	
    private static final long serialVersionUIpD = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "intitule")
    private String intitule;

    @Column(name = "domaine")
    private String domaine;

    @ManyToOne
    private FicheDeContact ficheDeContact;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public Formation intitule(String intitule) {
        this.intitule = intitule;
        return this;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getDomaine() {
        return domaine;
    }

    public Formation domaine(String domaine) {
        this.domaine = domaine;
        return this;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public FicheDeContact getFicheDeContact() {
        return ficheDeContact;
    }

    public Formation ficheDeContact(FicheDeContact ficheDeContact) {
        this.ficheDeContact = ficheDeContact;
        return this;
    }

    public void setFicheDeContact(FicheDeContact ficheDeContact) {
        this.ficheDeContact = ficheDeContact;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Formation)) {
            return false;
        }
        return id != null && id.equals(((Formation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Formation{" +
            "id=" + getId() +
            ", intitule='" + getIntitule() + "'" +
            ", domaine='" + getDomaine() + "'" +
            "}";
    }
}

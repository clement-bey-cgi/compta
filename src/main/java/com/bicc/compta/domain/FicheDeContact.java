package com.bicc.compta.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A FicheDeContact.
 */
@Entity
@Table(name = "fiche_de_contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FicheDeContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "genre")
    private Boolean genre;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "titre")
    private String titre;

    @Column(name = "mail")
    private String mail;

    @Column(name = "numero_de_telephone")
    private String numeroDeTelephone;

    @Column(name = "date_de_naissance")
    private LocalDate dateDeNaissance;

    @OneToOne(mappedBy = "ficheDeContact")
    @JsonIgnore
    private Cv cv;

    @OneToMany(mappedBy = "ficheDeContact")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Formation> formations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isGenre() {
        return genre;
    }

    public FicheDeContact genre(Boolean genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(Boolean genre) {
        this.genre = genre;
    }

    public String getNom() {
        return nom;
    }

    public FicheDeContact nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public FicheDeContact prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTitre() {
        return titre;
    }

    public FicheDeContact titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMail() {
        return mail;
    }

    public FicheDeContact mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNumeroDeTelephone() {
        return numeroDeTelephone;
    }

    public FicheDeContact numeroDeTelephone(String numeroDeTelephone) {
        this.numeroDeTelephone = numeroDeTelephone;
        return this;
    }

    public void setNumeroDeTelephone(String numeroDeTelephone) {
        this.numeroDeTelephone = numeroDeTelephone;
    }

    public LocalDate getDateDeNaissance() {
        return dateDeNaissance;
    }

    public FicheDeContact dateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
        return this;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public Cv getCv() {
        return cv;
    }

    public FicheDeContact cv(Cv cv) {
        this.cv = cv;
        return this;
    }

    public void setCv(Cv cv) {
        this.cv = cv;
    }

    public Set<Formation> getFormations() {
        return formations;
    }

    public FicheDeContact formations(Set<Formation> formations) {
        this.formations = formations;
        return this;
    }

    public FicheDeContact addFormation(Formation formation) {
        this.formations.add(formation);
        formation.setFicheDeContact(this);
        return this;
    }

    public FicheDeContact removeFormation(Formation formation) {
        this.formations.remove(formation);
        formation.setFicheDeContact(null);
        return this;
    }

    public void setFormations(Set<Formation> formations) {
        this.formations = formations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FicheDeContact)) {
            return false;
        }
        return id != null && id.equals(((FicheDeContact) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FicheDeContact{" +
            "id=" + getId() +
            ", genre='" + isGenre() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", titre='" + getTitre() + "'" +
            ", mail='" + getMail() + "'" +
            ", numeroDeTelephone='" + getNumeroDeTelephone() + "'" +
            ", dateDeNaissance='" + getDateDeNaissance() + "'" +
            "}";
    }
}

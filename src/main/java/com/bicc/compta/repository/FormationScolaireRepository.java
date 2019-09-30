package com.bicc.compta.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bicc.compta.domain.formation.FormationScolaire;


/**
 * Spring Data  repository for the Formation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormationScolaireRepository extends JpaRepository<FormationScolaire, Long> {

}
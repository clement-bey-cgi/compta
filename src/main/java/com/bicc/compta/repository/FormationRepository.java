package com.bicc.compta.repository;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.bicc.compta.domain.formation.Formation;


/**
 * Spring Data  repository for the Formation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {

}

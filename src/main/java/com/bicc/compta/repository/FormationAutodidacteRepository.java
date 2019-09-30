package com.bicc.compta.repository;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.bicc.compta.domain.formation.Formation;
import com.bicc.compta.domain.formation.FormationAutodidacte;


/**
 * Spring Data  repository for the Formation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormationAutodidacteRepository extends JpaRepository<FormationAutodidacte, Long> {

}

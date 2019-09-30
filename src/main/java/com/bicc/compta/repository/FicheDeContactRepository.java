package com.bicc.compta.repository;
import com.bicc.compta.domain.FicheDeContact;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FicheDeContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FicheDeContactRepository extends JpaRepository<FicheDeContact, Long> {

}

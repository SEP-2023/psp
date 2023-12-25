package com.project.auth_service.repository;

import com.project.auth_service.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AgencyRepository extends JpaRepository<Agency, Long> {
    @Query("SELECT l from Agency l WHERE l.agencyId=?1")
    Agency getAgencyById(String id);

    @Query("SELECT t from Agency t WHERE t.mail=?1")
    Agency getAgencyByMail(String mail);
}

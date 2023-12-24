package com.project.auth_service.repository;

import com.project.auth_service.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    @Query("SELECT l FROM PaymentMethod l WHERE l.agencyId=?1")
    List<PaymentMethod> findByAgencyId(String agencyId);
}

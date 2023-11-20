package com.project.paypal.repository;

import com.project.paypal.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("SELECT s FROM Subscription s WHERE s.token=?1")
    Subscription getSubscriptionByToken(String token);
}

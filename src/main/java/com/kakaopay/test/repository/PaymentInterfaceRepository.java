package com.kakaopay.test.repository;

import com.kakaopay.test.entity.PaymentInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PaymentInterfaceRepository extends JpaRepository<PaymentInterface, Long> {
    @Query
    Optional<PaymentInterface> findByManagementNo(Long managementNo);
}

package com.kakaopay.test.repository;

import com.kakaopay.test.entity.Payment;
import com.kakaopay.test.util.BooleanToStringConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("select p.canceled from Payment p where p.managementNo = :managementNo")
    Boolean isCanceled(@Param("managementNo") Long managementNo);
}

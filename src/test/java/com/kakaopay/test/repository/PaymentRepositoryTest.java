package com.kakaopay.test.repository;

import com.kakaopay.test.entity.Payment;
import com.kakaopay.test.entity.dto.CardInfo;
import com.kakaopay.test.util.CryptoUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class PaymentRepositoryTest {
    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    void paymentCrudTest() {
        CardInfo cardInfo = new CardInfo("1234567890123456", "1125", "777");
        Payment payment = new Payment();
        payment.setPaymentType("payment");
        payment.setCardInfo(cardInfo);
        payment.setInstallmentPlan("01");
        payment.setAmount(110000L);
        payment.setVat(10000L);
        paymentRepository.save(payment);

        Payment anotherPayment = paymentRepository.findById(payment.getManagementNo()).get();
        assertThat(anotherPayment.getCardInfo()).isEqualTo(cardInfo);
        assertThat(anotherPayment.getCardInfo().getCardNo()).isEqualTo("1234567890123456");

        log.info("anotherPayment : " + anotherPayment);
        log.info("encrypted Card Info : " + anotherPayment.getCardInfo().getEncryptedValue());
        log.info("decrypted Card Info : " + CryptoUtil.decrypt(anotherPayment.getCardInfo().getEncryptedValue()));
    }
}
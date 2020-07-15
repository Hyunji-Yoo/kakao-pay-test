package com.kakaopay.test.service;

import com.kakaopay.test.entity.Payment;
import com.kakaopay.test.entity.dto.CardInfo;
import com.kakaopay.test.repository.PaymentRepository;
import com.kakaopay.test.vo.CancelRequestVo;
import com.kakaopay.test.vo.PayRequestVo;
import com.kakaopay.test.vo.PaymentVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @Test
    void executePaymentTest() {
        PayRequestVo vo = getPayRequestVoInstance();
        try {
            PaymentVo result = paymentService.executePayment(vo);
            log.info("result 1 : " + result.toString());
            result = paymentService.getPaymentInfo(result.getManagementNo());
            log.info("result 2 : " + result.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Test
    void executeAndCancelPaymentTest() {
        PayRequestVo vo = getPayRequestVoInstance();
        try {
            PaymentVo result = paymentService.executePayment(vo);
            result = paymentService.getPaymentInfo(result.getManagementNo());
            log.info("result 1 : " + result.toString());

            CancelRequestVo cancelRequestVo = new CancelRequestVo();
            cancelRequestVo.setManagementNo(result.getManagementNo());
            cancelRequestVo.setAmount(result.getAmount());
            result = paymentService.cancelPayment(cancelRequestVo);
            log.info("result 2 : " + result.toString());

            result = paymentService.getPaymentInfo(result.getManagementNo());
            log.info("result 3 : " + result.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private PayRequestVo getPayRequestVoInstance() {
        PayRequestVo vo = new PayRequestVo();
        vo.setCardNo("1234567890123456");
        vo.setExpiryDate("1125");
        vo.setCvc("777");
        vo.setInstallmentPlan("0");
        vo.setAmount(110000L);
        vo.setVat(10000L);
        return vo;
    }
}
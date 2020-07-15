package com.kakaopay.test.controller;

import com.kakaopay.test.exception.KakaoException;
import com.kakaopay.test.service.PaymentService;
import com.kakaopay.test.vo.CancelRequestVo;
import com.kakaopay.test.vo.PayRequestVo;
import com.kakaopay.test.vo.PaymentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping(value = "/payment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PaymentVo executePayment(@RequestBody PayRequestVo vo) {
        try {
            log.info(vo.toString());
            PaymentVo result =  paymentService.executePayment(vo);
            log.info(result.toString());
            return result;
        } catch (Exception e) {
            throw new KakaoException(e);
        }
    }

    @DeleteMapping(value = "/payment/{managementNo}")
    public PaymentVo cancelPayment(@RequestBody CancelRequestVo vo) {
        try {
            return paymentService.cancelPayment(vo);
        } catch (Exception e) {
            throw new KakaoException(e);
        }
    }

    @GetMapping(value = "/payment/{managementNo}")
    public PaymentVo getPaymentInfo(@PathVariable String managementNo) throws Exception {
        try {
            return paymentService.getPaymentInfo(Long.parseLong(managementNo));
        } catch (Exception e) {
            throw new KakaoException(e);
        }
    }

    @ExceptionHandler(value = KakaoException.class)
    public String handleException(KakaoException e){
        log.error(e.toString());
        return e.getMessage();
    }
}

package com.kakaopay.test.service;

import com.kakaopay.test.entity.Payment;
import com.kakaopay.test.vo.CancelRequestVo;
import com.kakaopay.test.vo.PayRequestVo;
import com.kakaopay.test.vo.PaymentVo;

import java.util.List;

public interface PaymentService {
    public PaymentVo executePayment(PayRequestVo vo) throws Exception;
    public PaymentVo cancelPayment(CancelRequestVo vo) throws Exception;
    public PaymentVo getPaymentInfo(Long managementNo) throws Exception;
}

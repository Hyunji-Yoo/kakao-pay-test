package com.kakaopay.test.service;

import com.kakaopay.test.entity.Payment;
import com.kakaopay.test.entity.PaymentInterface;
import com.kakaopay.test.entity.dto.CardInfo;
import com.kakaopay.test.repository.PaymentInterfaceRepository;
import com.kakaopay.test.repository.PaymentRepository;
import com.kakaopay.test.util.VatUtil;
import com.kakaopay.test.vo.CancelRequestVo;
import com.kakaopay.test.vo.PayRequestVo;
import com.kakaopay.test.vo.PaymentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentInterfaceRepository paymentInterfaceRepository;

    @Override
    @Transactional
    public PaymentVo executePayment(PayRequestVo vo) throws Exception {
        log.info("executePayment - vo : " + vo);
        CardInfo cardInfo = new CardInfo(vo.getCardNo(), vo.getExpiryDate(), vo.getCvc());
        Payment payment = new Payment();
        payment.setPaymentType("PAYMENT");
        payment.setCardInfo(cardInfo);
        payment.setAmount(vo.getAmount());
        payment.setVat(vo.getVat() == null? VatUtil.calculateVat(vo.getAmount()) : vo.getVat());
        payment.setInstallmentPlan(vo.getInstallmentPlan());
        paymentRepository.save(payment);

        PaymentInterface paymentInterface = new PaymentInterface(payment);
        paymentInterfaceRepository.save(paymentInterface);

        PaymentVo result = new PaymentVo();
        result.setManagementNo(payment.getManagementNo());
        result.setEntireData(paymentInterface.getEntireData());
        return result;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public PaymentVo cancelPayment(CancelRequestVo vo) throws Exception {
        boolean cancelYN = paymentRepository.isCanceled(vo.getManagementNo());
        if (cancelYN) {
            throw new Exception();
        }
        Payment payment = setCancelFlag(vo);

        Payment cancellation = new Payment();
        cancellation.setPaymentType("CANCEL");
        cancellation.setOriginalManagementNo(String.valueOf(vo.getManagementNo()));
        cancellation.setAmount(vo.getAmount());
        cancellation.setVat(vo.getVat() == null? payment.getVat() : vo.getVat());
        cancellation.setCardInfo(payment.getCardInfo());
        cancellation.setInstallmentPlan(payment.getInstallmentPlan());
        cancellation.setVat(payment.getVat());
        paymentRepository.save(cancellation);

        PaymentInterface paymentInterface = new PaymentInterface(cancellation);
        paymentInterfaceRepository.save(paymentInterface);

        PaymentVo result = new PaymentVo();
        result.setManagementNo(paymentInterface.getManagementNo());
        result.setOriginalManagementNo(vo.getManagementNo());
        result.setEntireData(paymentInterface.getEntireData());
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentVo getPaymentInfo(Long managementNo) throws Exception {
        Optional<PaymentInterface> result = paymentInterfaceRepository.findByManagementNo(managementNo);
        if (result.isPresent()) {
            PaymentInterface paymentInterface = result.get();
            Payment payment = paymentRepository.findById(managementNo).get();
            PaymentVo returnVo = new PaymentVo();
            returnVo.setManagementNo(managementNo);
            returnVo.setCardNo(payment.getCardInfo().getMaskedCardNo());
            returnVo.setExpiryDate(payment.getCardInfo().getExpiryDate());
            returnVo.setCvc(payment.getCardInfo().getCvc());
            returnVo.setPaymentType(paymentInterface.getPaymentType());
            returnVo.setAmount(payment.getAmount());
            returnVo.setVat(payment.getVat());
            return returnVo;
        } else {
            throw new Exception();
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    Payment setCancelFlag(CancelRequestVo vo) throws Exception {
        Optional<Payment> paymentOptional = paymentRepository.findById(vo.getManagementNo());
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            payment.setCanceled(true);
            paymentRepository.save(payment);
            return payment;
        } else {
            throw new Exception();
        }
    }
}

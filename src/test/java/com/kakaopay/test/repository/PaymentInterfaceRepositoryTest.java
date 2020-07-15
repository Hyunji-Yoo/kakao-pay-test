package com.kakaopay.test.repository;

import com.kakaopay.test.entity.Payment;
import com.kakaopay.test.entity.dto.CardInfo;
import com.kakaopay.test.entity.PaymentInterface;
import com.kakaopay.test.util.StringFormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class PaymentInterfaceRepositoryTest {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentInterfaceRepository paymentInterfaceRepository;

    @Test
    void paymentInterfaceCrudTest() {
        Payment payment = givenPayment();
        PaymentInterface paymentInterface = new PaymentInterface(payment);
        paymentInterfaceRepository.save(paymentInterface);

        PaymentInterface result = paymentInterfaceRepository.findById(paymentInterface.getInterfaceNumber()).get();
        log.info(result.toString());

        assertThat(result.getHeader().length()).isEqualTo(34);
        assertThat(result.getBody().length()).isEqualTo(416);
        assertThat(result.getEntireData().length()).isEqualTo(450);

        String testData = " 446PAYMENT   1                   1234567890123456    001125777    1100000000010000                    9gT4zCLrgrAsybl2DknUmhrVynuYaxmCgK6Bst9ny0I=                                                                                                                                                                                                                                                                                                               ";
        assertThat(result.getEntireData()).isEqualTo(testData);
    }

    @Test
    void checkDataBodyLength() {
        Payment payment = givenPayment();
        StringBuilder sb = new StringBuilder();

        String cardNo = StringFormatUtil.rpadWithEmptyString(payment.getCardInfo().getCardNo(), 20);
        assertThat(cardNo.length()).isEqualTo(20);
        sb.append(cardNo);

        String installment = StringFormatUtil.padWithZero(payment.getInstallmentPlan(), 2);
        assertThat(installment.length()).isEqualTo(2);
        sb.append(installment);

        String expiryDate = StringFormatUtil.rpadWithEmptyString(payment.getCardInfo().getExpiryDate(), 4);
        assertThat(expiryDate.length()).isEqualTo(4);
        sb.append(expiryDate);

        String cvc = StringFormatUtil.rpadWithEmptyString(payment.getCardInfo().getCvc(),3);
        assertThat(cvc.length()).isEqualTo(3);
        sb.append(cvc);

        String amount = StringFormatUtil.padWithEmptyString(payment.getAmount(), 10);
        assertThat(amount.length()).isEqualTo(10);
        sb.append(amount);

        String vat = StringFormatUtil.padWithZero(payment.getVat(),10);
        assertThat(vat.length()).isEqualTo(10);
        sb.append(vat);

        String originalManagementNo = StringFormatUtil.getEmptyString(20);
        assertThat(originalManagementNo.length()).isEqualTo(20);
        sb.append(originalManagementNo);

        String encryptedCardInfo = StringFormatUtil.rpadWithEmptyString(payment.getCardInfo().getEncryptedValue(), 300);
        assertThat(encryptedCardInfo.length()).isEqualTo(300);
        sb.append(encryptedCardInfo);

        String reserves = StringFormatUtil.getEmptyString(47);
        assertThat(reserves.length()).isEqualTo(47);
        sb.append(reserves);

        String body = sb.toString();
        log.info("body : " + body);
        assertThat(body.length()).isEqualTo(416);
        //paymentInterface.setBody(sb.toString());
    }

    @Test
    void checkDataHeaderLength() {
        Payment payment = givenPayment();
        StringBuilder sb = new StringBuilder();

        String dataLength = StringFormatUtil.padWithEmptyString("446", 4);
        assertThat(dataLength.length()).isEqualTo(4);
        sb.append(dataLength);

        String paymentType = StringFormatUtil.rpadWithEmptyString(payment.getPaymentType(), 10);
        assertThat(paymentType.length()).isEqualTo(10);
        sb.append(paymentType);

        String managementNo = StringFormatUtil.rpadWithEmptyString(payment.getManagementNo(), 20);
        assertThat(managementNo.length()).isEqualTo(20);
        sb.append(managementNo);

        String header = sb.toString();
        log.info("header : " + header);
        assertThat(header.length()).isEqualTo(34);
    }

    private Payment givenPayment() {
        CardInfo cardInfo = new CardInfo("1234567890123456", "1125", "777");
        Payment payment = new Payment();
        payment.setPaymentType("PAYMENT");
        payment.setCardInfo(cardInfo);
        payment.setInstallmentPlan("0");
        payment.setAmount(110000L);
        payment.setVat(10000L);
        paymentRepository.save(payment);
        return payment;
    }
}
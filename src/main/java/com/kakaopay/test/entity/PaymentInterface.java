package com.kakaopay.test.entity;

import com.kakaopay.test.util.StringFormatUtil;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "PAYMENT_INTERFACE")
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class PaymentInterface {
    @Id
    @GeneratedValue
    @Column(name = "INTERFACE_NUMBER")
    private Long interfaceNumber;

    @Column(name = "HEADER", updatable = false, length = 34)
    private String header;

    @Column(name = "BODY", updatable = false, length = 416)
    private String body;

    @Column(name = "ENTIRE_DATA", updatable = false, length = 450)
    private String entireData;

    @Column(name = "MANAGEMENT_NO", updatable = false, length = 20)
    private Long managementNo;

    @Column(name = "PAYMENT_TYPE", nullable = false, length = 10)
    private String paymentType;

    public PaymentInterface(Payment payment) {
        setEntireData(payment);
    }

    private void setEntireData(Payment payment) {
        this.managementNo = payment.getManagementNo();
        this.paymentType = payment.getPaymentType();
        setHeader(payment);
        setBody(payment);
        this.entireData = new StringBuffer(this.header).append(this.body).toString();
    }

    private void setHeader(Payment payment) {
        StringBuilder sb = new StringBuilder();
        sb.append(StringFormatUtil.padWithEmptyString("446", 4));
        sb.append(StringFormatUtil.rpadWithEmptyString(payment.getPaymentType(), 10));
        sb.append(StringFormatUtil.rpadWithEmptyString(payment.getManagementNo(), 20));
        this.header = sb.toString();
    }

    private void setBody(Payment payment) {
        StringBuilder sb = new StringBuilder();
        sb.append(StringFormatUtil.rpadWithEmptyString(payment.getCardInfo().getCardNo(), 20));
        sb.append(StringFormatUtil.padWithZero(payment.getInstallmentPlan(), 2));
        sb.append(StringFormatUtil.rpadWithEmptyString(payment.getCardInfo().getExpiryDate(), 4));
        sb.append(StringFormatUtil.rpadWithEmptyString(payment.getCardInfo().getCvc(),3));
        sb.append(StringFormatUtil.padWithEmptyString(payment.getAmount(), 10));
        sb.append(StringFormatUtil.padWithZero(payment.getVat(),10));
        sb.append(StringFormatUtil.rpadWithEmptyString(payment.getOriginalManagementNo(), 20));
        sb.append(StringFormatUtil.rpadWithEmptyString(payment.getCardInfo().getEncryptedValue(), 300));
        sb.append(StringFormatUtil.getEmptyString(47));
        this.body = sb.toString();
    }
}

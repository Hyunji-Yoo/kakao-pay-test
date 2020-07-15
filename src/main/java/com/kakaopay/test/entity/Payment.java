package com.kakaopay.test.entity;

import com.kakaopay.test.entity.dto.CardInfo;
import com.kakaopay.test.util.BooleanToStringConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "PAYMENT")
@Data
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue
    @Column(name = "MANAGEMENT_NO", updatable = false, length = 20)
    private Long managementNo;

    @Column(name = "PAYMENT_TYPE", nullable = false, length = 10)
    private String paymentType = "PAYMENT";

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="cardNo", column=@Column(name="PAYMENT_CARD_NO"))
            ,@AttributeOverride(name="expiryDate", column=@Column(name="PAYMENT_EXPIRY_DATE"))
            ,@AttributeOverride(name="cvc", column=@Column(name="PAYMENT_CVC"))
            ,@AttributeOverride(name="encryptedValue", column=@Column(name="PAYMENT_ENCRYPTED_VALUE"))
    })
    private CardInfo cardInfo;

    @Column(name = "INSTALLMENT_PLAN", nullable = false, length = 2)
    private String installmentPlan = "00";

    @Column(name = "AMOUNT", nullable = false, length = 10)
    private Long amount;

    @Column(name = "VAT", nullable = false, length = 10)
    private Long vat;

    @Column(name = "ORIGINAL_MANAGEMENT_NO", length = 20)
    private String originalManagementNo = "";

    @Column(name = "RESERVE", length = 47)
    private String reserve = "";

    @Convert(converter = BooleanToStringConverter.class)
    @Column(name = "CANCELED", length = 1)
    private Boolean canceled = false;
}
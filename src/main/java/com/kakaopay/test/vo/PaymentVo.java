package com.kakaopay.test.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentVo {
    private Long managementNo;
    private Long amount;
    private Long vat;
    private String paymentType;
    private Long originalManagementNo;
    private String entireData;
    private String cardNo;
    private String expiryDate;
    private String cvc;
}
package com.kakaopay.test.vo;

import lombok.Data;

@Data
public class PayRequestVo {
    private String cardNo;
    private String expiryDate;
    private String cvc;
    private String installmentPlan;
    private Long amount;
    private Long vat;
}

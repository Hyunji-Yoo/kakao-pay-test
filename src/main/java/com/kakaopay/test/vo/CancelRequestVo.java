package com.kakaopay.test.vo;

import lombok.Data;

@Data
public class CancelRequestVo {
    private Long managementNo;
    private Long amount;
    private Long vat;
    private String paymentType;
    private Long cancelManagementNo;
}

package com.kakaopay.test.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VatUtil {

    public static long calculateVat(long amount) {
        long vat = Math.round((double)amount/11);
        log.info("vat :" + vat);
        return vat;
    }
}

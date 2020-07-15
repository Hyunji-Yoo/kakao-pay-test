package com.kakaopay.test.util;


import org.apache.commons.lang3.StringUtils;

public class StringFormatUtil {
    public static String padWithEmptyString(String value, int length) {
        return StringUtils.leftPad(value, length);
    }

    public static String padWithZero(String value, int length) {
        return StringUtils.leftPad(value, length, '0');
    }

    public static String rpadWithEmptyString(String value, int length) {
        return StringUtils.rightPad(value, length);
    }

    public static String padWithEmptyString(Long value, int length) {
        return StringFormatUtil.padWithEmptyString(String.valueOf(value), length);
    }

    public static String padWithZero(Long value, int length) {
        return StringFormatUtil.padWithZero(String.valueOf(value), length);
    }

    public static String rpadWithEmptyString(Long value, int length) {
        return StringFormatUtil.rpadWithEmptyString(String.valueOf(value), length);
    }

    public static String getEmptyString(int length) {
        return StringUtils.repeat(" ", length);
    }
}

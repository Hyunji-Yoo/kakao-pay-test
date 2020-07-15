package com.kakaopay.test.entity.dto;

import com.kakaopay.test.util.CryptoUtil;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

@Embeddable
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class CardInfo implements Externalizable {
    private static final long serialVersionUID = 1L;

    @Column(name = "CARD_NO", length = 20)
    private String cardNo;

    @Column(name = "EXPIRY_DATE", length = 4)
    private String expiryDate;

    @Column(name = "CVC", length = 3)
    private String cvc;

    @Column(name = "ENCRYPTED_VALUE")
    private String encryptedValue;

    public CardInfo(String cardNo, String expiryDate, String cvc) {
        this.cardNo = cardNo;
        this.expiryDate = expiryDate;
        this.cvc = cvc;

        StringBuilder sb = new StringBuilder();
        sb.append(this.cardNo).append("|").append(this.expiryDate).append("|").append(this.cvc);
        encryptedValue = CryptoUtil.encrypt(sb.toString());
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public String getMaskedCardNo() {
        return getMaskedCardNo(this.cardNo);
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public String getCvc() {
        return this.cvc;
    }

    public String getEncryptedValue() {
        return this.encryptedValue;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.encryptedValue);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        String encrypted = (String) in.readObject();
        String[] values = (CryptoUtil.decrypt(encrypted)).split("\\|");
        this.cardNo = values[0];
        this.expiryDate = values[1];
        this.cvc = values[2];
    }

    private String getMaskedCardNo(String cardNo) {
        StringBuilder sb = new StringBuilder();
        char[] charArray = cardNo.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (6 <= i && i < charArray.length-3) sb.append("*");
            else sb.append(charArray[i]);
        }
        return sb.toString();
    }
}
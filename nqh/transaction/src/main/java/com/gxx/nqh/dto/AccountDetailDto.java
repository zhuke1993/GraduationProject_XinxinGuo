package com.gxx.nqh.dto;

import java.math.BigDecimal;

/**
 * Created by GXX on 2016/4/8.
 */
public class AccountDetailDto {
    private String bankCardNo;
    private BigDecimal amount;

    public AccountDetailDto(String bankCardNo, BigDecimal amount) {
        this.bankCardNo = bankCardNo;
        this.amount = amount;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

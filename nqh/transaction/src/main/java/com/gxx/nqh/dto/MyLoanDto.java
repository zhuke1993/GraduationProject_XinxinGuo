package com.gxx.nqh.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 我的借款列表
 * Created by ZHUKE on 2016/4/3.
 */
public class MyLoanDto {
    private String loanTitle;
    private Date date;
    private BigDecimal amount;

    public String getLoanTitle() {
        return loanTitle;
    }

    public void setLoanTitle(String loanTitle) {
        this.loanTitle = loanTitle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

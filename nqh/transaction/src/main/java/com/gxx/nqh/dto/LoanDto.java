package com.gxx.nqh.dto;

import java.math.BigDecimal;

/**
 * Created by ZHUKE on 2016/4/8.
 */
public class LoanDto {
    private Long agreementId;
    private String loanTitle;
    private BigDecimal amount;
    private String date;
    private String loanUserName;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoanUserName() {
        return loanUserName;
    }

    public void setLoanUserName(String loanUserName) {
        this.loanUserName = loanUserName;
    }

    public Long getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(Long agreementId) {
        this.agreementId = agreementId;
    }

    public String getLoanTitle() {
        return loanTitle;
    }

    public void setLoanTitle(String loanTitle) {
        this.loanTitle = loanTitle;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LoanDto() {
    }

    public LoanDto(Long agreementId, String loanTitle, BigDecimal amount, String date, String loanUserName, String status) {
        this.agreementId = agreementId;
        this.loanTitle = loanTitle;
        this.amount = amount;
        this.date = date;
        this.loanUserName = loanUserName;
        this.status = status;
    }
}

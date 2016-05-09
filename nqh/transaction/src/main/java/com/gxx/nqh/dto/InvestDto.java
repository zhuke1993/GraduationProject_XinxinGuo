package com.gxx.nqh.dto;

/**
 * Created by ZHUKE on 2016/4/8.
 */
public class InvestDto {
    private Long agreementRequestId;
    private Long agreementId;
    private String loanTitle;
    private String amount;
    private String date;

    public Long getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(Long agreementId) {
        this.agreementId = agreementId;
    }

    public Long getAgreementRequestId() {
        return agreementRequestId;
    }

    public void setAgreementRequestId(Long agreementRequestId) {
        this.agreementRequestId = agreementRequestId;
    }

    public String getLoanTitle() {
        return loanTitle;
    }

    public void setLoanTitle(String loanTitle) {
        this.loanTitle = loanTitle;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public InvestDto(Long agreementRequestId, Long agreementId, String loanTitle, String amount, String date) {
        this.agreementRequestId = agreementRequestId;
        this.agreementId = agreementId;
        this.loanTitle = loanTitle;
        this.amount = amount;
        this.date = date;
    }
}

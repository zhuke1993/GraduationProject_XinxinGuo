package com.gxx.nqh.dto;

import java.math.BigDecimal;

/**
 * Created by ZHUKE on 2016/3/29.
 */
public class AgreementSummaryDto {
    private Long agreementId;
    private String loanTitle;
    private String schoolName;
    private String status;
    private BigDecimal rateMonthly;
    private int repaymentLimit;
    private BigDecimal amount;
    private int creditScore;
    private String creditScoreStr;

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public String getCreditScoreStr() {
        return creditScoreStr;
    }

    public void setCreditScoreStr(String creditScoreStr) {
        this.creditScoreStr = creditScoreStr;
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

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getRateMonthly() {
        return rateMonthly;
    }

    public void setRateMonthly(BigDecimal rateMonthly) {
        this.rateMonthly = rateMonthly;
    }

    public int getRepaymentLimit() {
        return repaymentLimit;
    }

    public void setRepaymentLimit(int repaymentLimit) {
        this.repaymentLimit = repaymentLimit;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

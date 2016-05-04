package com.gxx.nqh.dto;

import java.math.BigDecimal;

/**
 * Created by ZHUKE on 2016/3/29.
 */
public class AgreementDetailDto {
    private String loanTitle;
    private String schoolName;
    private String status;
    private BigDecimal rateMonthly;
    private int repaymentLimit;
    private BigDecimal amount;

    private String loanFor;
    private String description;

    private String loanUserName;
    private String address;

    private BigDecimal raisedAmount;
    private BigDecimal raisedLimitationAmount;

    private int loanTimes;
    private int badLoanTimes;

    //private ArrayList<FileURL> files;

    public BigDecimal getRaisedAmount() {
        return raisedAmount;
    }

    public void setRaisedAmount(BigDecimal raisedAmount) {
        this.raisedAmount = raisedAmount;
    }

    public BigDecimal getRaisedLimitationAmount() {
        return raisedLimitationAmount;
    }

    public void setRaisedLimitationAmount(BigDecimal raisedLimitationAmount) {
        this.raisedLimitationAmount = raisedLimitationAmount;
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

    public String getLoanFor() {
        return loanFor;
    }

    public void setLoanFor(String loanFor) {
        this.loanFor = loanFor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLoanUserName() {
        return loanUserName;
    }

    public void setLoanUserName(String loanUserName) {
        this.loanUserName = loanUserName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getLoanTimes() {
        return loanTimes;
    }

    public void setLoanTimes(int loanTimes) {
        this.loanTimes = loanTimes;
    }

    public int getBadLoanTimes() {
        return badLoanTimes;
    }

    public void setBadLoanTimes(int badLoanTimes) {
        this.badLoanTimes = badLoanTimes;
    }

   /* public ArrayList<FileURL> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<FileURL> files) {
        this.files = files;
    }*/
}

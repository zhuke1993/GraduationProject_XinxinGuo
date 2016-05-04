package com.gxx.nqh.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 借贷合同
 * Created by ZHUKE on 2016/3/20.
 */
@Entity
@Table(name = "agreement")
public class Agreement {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "modified_on")
    private Date modifiedOn;

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "user_id")
    private Long userId;

    /**
     * 借款用途
     */
    @Column(name = "loan_for")
    private String loanFor;

    @Column(name = "loan_title")
    private String loanTitle;

    @Column(scale = 2)
    private BigDecimal amount;

    @Column(name = "rate_monthly", scale = 2)
    private BigDecimal rateMonthly;//利率

    @Column
    private String description;

    @Column(name = "repayment_limit")
    private int repaymentLimit;

    @Column
    private String status;

    @Column(name = "raised_amount")
    private BigDecimal raisedAmount;

    @Column(name = "raised_limitation")
    private BigDecimal raisedLimitation;

    public BigDecimal getRaisedLimitation() {
        return raisedLimitation;
    }

    public void setRaisedLimitation(BigDecimal raisedLimitation) {
        this.raisedLimitation = raisedLimitation;
    }

    public BigDecimal getRaisedAmount() {
        return raisedAmount;
    }

    public void setRaisedAmount(BigDecimal raisedAmount) {
        this.raisedAmount = raisedAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getRepaymentLimit() {
        return repaymentLimit;
    }

    public void setRepaymentLimit(int repaymentLimit) {
        this.repaymentLimit = repaymentLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getLoanFor() {
        return loanFor;
    }

    public void setLoanFor(String loanFor) {
        this.loanFor = loanFor;
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

    public BigDecimal getRateMonthly() {
        return rateMonthly;
    }

    public void setRateMonthly(BigDecimal rateMonthly) {
        this.rateMonthly = rateMonthly;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Agreement{" +
                "id=" + id +
                ", createdOn=" + createdOn +
                ", modifiedOn=" + modifiedOn +
                ", accountId=" + accountId +
                ", loanFor='" + loanFor + '\'' +
                ", loanTitle='" + loanTitle + '\'' +
                ", amount=" + amount +
                ", rateMonthly=" + rateMonthly +
                ", description='" + description + '\'' +
                ", repaymentLimit=" + repaymentLimit +
                ", status='" + status + '\'' +
                '}';
    }
}

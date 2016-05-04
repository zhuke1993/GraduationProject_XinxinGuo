package com.gxx.nqh.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同请求
 * Created by ZHUKE on 2016/3/20.
 */
@Entity
@Table(name = "agreement_request")
public class AgreementRequest {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "modified_on")
    private Date modifiedOn;

    @Column(name = "agreement_id")
    private Long agreementId;

    @Column(name = "request_type")
    private String requestType;

    @Column
    private String status;

    @Column(name = "source_request_id")
    private Long sourceRequestId;

    @Column(name = "target_request_id")
    private Long targetRequestId;

    @Column
    private String remark;

    @Column(name = "opt_user_id")
    private Long optUserId;

    @Column(name = "opt_account_id")
    private Long optAccountId;

    @Column
    private BigDecimal amount;

    public Long getOptAccountId() {
        return optAccountId;
    }

    public void setOptAccountId(Long optAccountId) {
        this.optAccountId = optAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public Long getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(Long agreementId) {
        this.agreementId = agreementId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSourceRequestId() {
        return sourceRequestId;
    }

    public void setSourceRequestId(Long sourceRequestId) {
        this.sourceRequestId = sourceRequestId;
    }

    public Long getTargetRequestId() {
        return targetRequestId;
    }

    public void setTargetRequestId(Long targetRequestId) {
        this.targetRequestId = targetRequestId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getOptUserId() {
        return optUserId;
    }

    public void setOptUserId(Long optUserId) {
        this.optUserId = optUserId;
    }
}

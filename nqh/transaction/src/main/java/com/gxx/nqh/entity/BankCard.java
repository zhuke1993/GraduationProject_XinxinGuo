package com.gxx.nqh.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 银行卡
 * Created by ZHUKE on 2016/3/20.
 */
@Entity
@Table(name = "bank_card")
public class BankCard {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "modified_on")
    private Date modifiedOn;

    @Column(name = "bank_card_no")
    private String bankCardNo;

    @Column(name = "bank_owner_name")
    private String bankOwenerName;

    @Column
    private String status;

    @Column(name = "bind_date")
    private Date bindDate;

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

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankOwenerName() {
        return bankOwenerName;
    }

    public void setBankOwenerName(String bankOwenerName) {
        this.bankOwenerName = bankOwenerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBindDate() {
        return bindDate;
    }

    public void setBindDate(Date bindDate) {
        this.bindDate = bindDate;
    }

    @Override
    public String toString() {
        return "BankCard{" +
                "id=" + id +
                ", createdOn=" + createdOn +
                ", modifiedOn=" + modifiedOn +
                ", bankCardNo='" + bankCardNo + '\'' +
                ", bankOwenerName='" + bankOwenerName + '\'' +
                ", status='" + status + '\'' +
                ", bindDate=" + bindDate +
                '}';
    }
}

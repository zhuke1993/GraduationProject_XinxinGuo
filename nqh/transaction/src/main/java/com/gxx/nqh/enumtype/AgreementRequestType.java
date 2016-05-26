package com.gxx.nqh.enumtype;

/**
 * Created by GXX on 2016/3/28.
 */
public enum AgreementRequestType {
    CREATEAGREEMENT("create_agreement"),
    INVESTMENT("investment"),
    REPAYMENT("repayment"),
    AUDITEDSUCCESS("audited success"),
    AUDITEDFIELD("audited field");
    private String value;

    public String getValue() {
        return value;
    }

    AgreementRequestType(String value) {
        this.value = value;
    }
}

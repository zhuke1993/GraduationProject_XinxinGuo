package com.gxx.nqh.enumtype;

/**
 * 产生accountentry的原因
 * Created by GXX on 2016/4/3.
 */
public enum AccountEntryType {
    INVEST("invest", "invest"), RECHARGE("recharge", "recharge"), WITHDRAW("withdraw", "withdraw"), REPAYMENT("repayment", "repaymet");

    String name;
    String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    AccountEntryType(String name, String value) {
        this.name = name;
        this.value = value;
    }
}

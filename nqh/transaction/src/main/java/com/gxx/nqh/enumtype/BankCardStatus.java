package com.gxx.nqh.enumtype;

/**
 * Created by GXX on 2016/3/28.
 */
public enum BankCardStatus {
    BINDED("BINDED", "绑定"), CANCELED("CANCELED", "取消");
    private String name;
    private String value;

    BankCardStatus(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}

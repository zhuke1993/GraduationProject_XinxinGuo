package com.gxx.nqh.enums;

/**
 * 信用等级
 * Created by GXX on 2016/5/20.
 */
public enum CreditLevelEnum {
    A("A"), B("B"), C("C"), D("D"), A_PLUS("A+"), S("S");
    private String value;

    CreditLevelEnum(String value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

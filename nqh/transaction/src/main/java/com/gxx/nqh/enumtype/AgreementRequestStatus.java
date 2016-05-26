package com.gxx.nqh.enumtype;

/**
 * Created by GXX on 2016/3/28.
 */
public enum AgreementRequestStatus {
    BEINGCREATED("being_created", "创建中"), RAISING("raising", "筹集中"), ENDED("ended", "已完成");
    private String name;
    private String value;

    AgreementRequestStatus(String name, String value) {
        this.name = name;
        this.value = value;
    }

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
}

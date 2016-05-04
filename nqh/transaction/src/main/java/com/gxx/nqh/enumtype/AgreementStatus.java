package com.gxx.nqh.enumtype;

/**
 * Created by ZHUKE on 2016/3/28.
 */
public enum AgreementStatus {
    BEINGCREATED("创建中", "创建中"),
    IN_RAISING("筹集中", "筹集中"),
    RAISING_COMPLETED("已完成", "已完成"),
    IN_REPAYMENT("还款中", "还款中"),
    REPAYMENT_COMPLETED("还款完成", "还款完成");
    private String name;
    private String value;

    AgreementStatus(String name, String value) {
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

    public static boolean isInEnum(String status) {
        for (AgreementStatus as : AgreementStatus.values()) {
            if (status.equals(as.getValue())) {
                return true;
            }
        }
        return false;
    }
}

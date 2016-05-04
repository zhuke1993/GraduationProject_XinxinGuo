package com.gxx.nqh.enumtype;

/**
 * Created by ZHUKE on 2016/3/29.
 */
public enum GrantedAuthorityEnum {
    ADMIN("admin"), USER("user");
    private String value;

    public String getValue() {
        return this.value;
    }

    GrantedAuthorityEnum(String value) {
        this.value = value;
    }
}

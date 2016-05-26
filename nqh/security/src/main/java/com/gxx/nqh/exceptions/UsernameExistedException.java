package com.gxx.nqh.exceptions;

/**
 * 用户名已存在
 * Created by GXX on 2016/3/28.
 */
public class UsernameExistedException extends RuntimeException {
    public UsernameExistedException(String message) {
        super(message);
    }
}

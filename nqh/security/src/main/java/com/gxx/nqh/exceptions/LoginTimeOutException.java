package com.gxx.nqh.exceptions;

/**
 * Created by ZHUKE on 2016/3/29.
 */
public class LoginTimeOutException extends RuntimeException {
    public LoginTimeOutException(String message) {
        super(message);
    }
}

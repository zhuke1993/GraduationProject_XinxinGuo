package com.gxx.nqh.service;

/**
 * Created by ZHUKE on 2016/3/28.
 */
public interface MailService extends  Runnable {
    void setMailInfo(String toAddr, String content);
    boolean sendMail(String toAddr, String content);
}

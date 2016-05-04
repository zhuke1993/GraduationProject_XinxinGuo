package com.gxx.nqh.service;

import com.gxx.nqh.entity.UserInfo;

import java.security.NoSuchAlgorithmException;

/**
 * Created by ZHUKE on 2016/3/27.
 */
public interface RegisterationService {
    void register(UserInfo userInfo);

    boolean login(String name, String password);

    /**
     * 初始化用户帐户
     *
     * @param userId
     */
    void initAccount(long userId);
}

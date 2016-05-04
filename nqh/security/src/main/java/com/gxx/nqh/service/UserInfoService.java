package com.gxx.nqh.service;

import com.gxx.nqh.dto.ProfileDto;
import com.gxx.nqh.entity.Account;
import com.gxx.nqh.entity.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ZHUKE on 2016/3/27.
 */
public interface UserInfoService {

    public Account getAccount(Long userId);

    UserInfo getUserInfo(String userName, String password);

    UserInfo getUserInfo(String userName);

    void addUserInfo(UserInfo userInfo);

    void modifyUserInfo(UserInfo userInfo);

    void delUserInfo(UserInfo userInfo);

    boolean isContainUserInfo(String userName);

    Account getAccount(String username);

    UserInfo getLoginUser(HttpServletRequest request);

    UserInfo getLoginUser(String token);

    List<ProfileDto> getProfileDtoList();

    void auditUser(String userName);
}

package com.gxx.nqh.service.impl;

import com.gxx.nqh.entity.Account;
import com.gxx.nqh.entity.UserInfo;
import com.gxx.nqh.exceptions.UsernameExistedException;
import com.gxx.nqh.service.RegisterationService;
import com.gxx.nqh.service.UserInfoService;
import com.gxx.nqh.util.MD5Util;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by GXX on 2016/3/27.
 */
@Service
public class RegisterationServiceImpl implements RegisterationService {

    private Logger logger = LogManager.getLogger(RegisterationService.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Transactional
    public void register(UserInfo userInfo) {
        UserInfo userInfo1 = userInfoService.getUserInfo(userInfo.getUserName());
        if (userInfo1 != null) {
            throw new UsernameExistedException("The username = " + userInfo.getUserName() + " has already existed! please change a new one.");
        }
        userInfoService.addUserInfo(userInfo);
        initAccount(userInfo.getId());

    }

    public boolean login(String name, String password) {
        UserInfo userInfo = userInfoService.getUserInfo(name);
        if (userInfo != null) {
            //密码加盐进行md5运算
            String passwordMD5 = MD5Util.string2MD5(password + userInfo.getGuid());

            if (userInfo.getPassword().equals(passwordMD5)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 初始化新建一个account
     *
     * @param userId
     */
    @Transactional
    public void initAccount(long userId) {

        Account account = new Account();
        account.setUserId(userId);
        account.setAmount(new BigDecimal(0));
        account.setCreatedOn(new Date());

        hibernateTemplate.save(account);
        logger.info("Account initialize success, the userId = " + userId);
    }


}

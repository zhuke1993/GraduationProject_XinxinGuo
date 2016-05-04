package com.gxx.nqh.service.impl;

import com.gxx.nqh.dto.ProfileDto;
import com.gxx.nqh.entity.Account;
import com.gxx.nqh.entity.FileURL;
import com.gxx.nqh.entity.UserInfo;
import com.gxx.nqh.exceptions.LoginTimeOutException;
import com.gxx.nqh.exceptions.NoPermitionException;
import com.gxx.nqh.util.PostRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import com.gxx.nqh.service.UserInfoService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHUKE on 2016/3/27.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public UserInfo getUserInfo(String userName, String password) {
        List<UserInfo> userInfo = (List<UserInfo>) hibernateTemplate.find("from UserInfo u where u.userName = ? and u.password = ? ", userName, password);
        if (userInfo.size() > 0) {
            return (UserInfo) userInfo.get(0);
        }
        return null;
    }

    public UserInfo getUserInfo(String userName) {
        List<UserInfo> userInfo = (List<UserInfo>) hibernateTemplate.find("from UserInfo u where u.userName = ?", userName);
        if (userInfo.size() > 0) {
            return (UserInfo) userInfo.get(0);
        }
        return null;
    }

    @Transactional
    public void addUserInfo(UserInfo userInfo) {
        hibernateTemplate.save(userInfo);
    }

    @Transactional
    public void modifyUserInfo(UserInfo userInfo) {
        hibernateTemplate.update(userInfo);
    }

    @Transactional
    public void delUserInfo(UserInfo userInfo) {
        hibernateTemplate.delete(userInfo);
    }

    public boolean isContainUserInfo(String userName) {
        return false;
    }


    public Account getAccount(String username) {
        UserInfo userInfo = getUserInfo(username);
        Account account = (Account) hibernateTemplate.find("from Account a where a.userId = ?", userInfo.getId()).get(0);
        return account;
    }

    public Account getAccount(Long userId) {
        Account account = (Account) hibernateTemplate.find("from Account a where a.userId = ?", userId).get(0);
        return account;
    }


    public UserInfo getLoginUser(HttpServletRequest request) {
        String token = null;
        token = request.getParameter("accessToken");
        if (StringUtils.isEmpty(redisTemplate.opsForValue().get(token))) {
            //登陆失效
            throw new LoginTimeOutException("登陆失效");
        } else {
            Long userId = Long.parseLong(redisTemplate.opsForValue().get(token));
            return hibernateTemplate.get(UserInfo.class, userId);
        }

    }

    public UserInfo getLoginUser(String token) {
        if (StringUtils.isEmpty(redisTemplate.opsForValue().get(token))) {
            //登陆失效
            throw new LoginTimeOutException("登陆失效");
        } else {
            Long userId = Long.parseLong(redisTemplate.opsForValue().get(token));
            return hibernateTemplate.get(UserInfo.class, userId);
        }

    }

    @Transactional
    public void auditUser(String userName) {
        UserInfo userInfo = (UserInfo) hibernateTemplate.find("from UserInfo u where u.userName=?", userName).get(0);
        short isAvailable = 1;
        userInfo.setIsAvailable(isAvailable);
        hibernateTemplate.update(userInfo);
    }

    public List<ProfileDto> getProfileDtoList() {
        List<ProfileDto> profileDtoList = new ArrayList<ProfileDto>();
        List<UserInfo> userInfoList = (List<UserInfo>) hibernateTemplate.find("from UserInfo where isAvailable = 0 and idcard_no is not null");
        for (UserInfo userInfo : userInfoList) {
            profileDtoList.add(new ProfileDto(1, userInfo.getUserName(),
                    userInfo.getRealname(),
                    userInfo.getIdcardNo(),
                    userInfo.getSchoolName(),
                    userInfo.getAddDetail(),
                    hibernateTemplate.get(FileURL.class, userInfo.getSchoolCertification()).getFileURL(),
                    hibernateTemplate.get(FileURL.class, userInfo.getIdCertification()).getFileURL()));
        }
        return profileDtoList;
    }

}

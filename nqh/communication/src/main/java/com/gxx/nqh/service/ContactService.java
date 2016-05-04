package com.gxx.nqh.service;

import com.gxx.nqh.entity.Contact;

import java.util.List;

/**
 * 处理联系人名片
 * Created by ZHUKE on 2016/4/4.
 */
public interface ContactService {
    void saveFriendShip(Long userId, List<Contact> contactList);
}

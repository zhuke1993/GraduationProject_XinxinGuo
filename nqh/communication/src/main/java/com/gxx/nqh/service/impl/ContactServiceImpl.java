package com.gxx.nqh.service.impl;

import com.gxx.nqh.entity.Contact;
import com.gxx.nqh.entity.Friendship;
import com.gxx.nqh.entity.UserInfo;
import com.gxx.nqh.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by GXX on 2016/4/4.
 */
@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private HibernateTemplate hibernateTemplate;


    @Transactional
    public void saveFriendShip(Long userId, List<Contact> contactList) {
        Iterator<Contact> it = contactList.iterator();
        while (it.hasNext()) {
            Contact next = it.next();
            ArrayList<UserInfo> userInfoList = (ArrayList<UserInfo>) hibernateTemplate.find("from UserInfo u where u.phoneNo = ?", next.getPhoneNumber());
            if (userInfoList.size() != 0) {
                Friendship friendship = new Friendship();
                friendship.setCreatedOn(new Date());
                friendship.setUserId(userId);
                friendship.setFriendId(userInfoList.get(0).getId());

                if (!isExistedFriendShip(userId, userInfoList.get(0).getId())) {
                    hibernateTemplate.save(friendship);
                }
            }
        }
    }

    public boolean isExistedFriendShip(Long userId, Long friendId) {
        List<Friendship> list = (List<Friendship>) hibernateTemplate.find("from Friendship f where f.userId = ? and f.friendId = ?", userId, friendId);
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        return true;
    }
}

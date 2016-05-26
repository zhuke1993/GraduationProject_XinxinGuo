package com.gxx.nqh.service.impl;

import com.gxx.nqh.dto.FriendDto;
import com.gxx.nqh.entity.Friendship;
import com.gxx.nqh.entity.UserInfo;
import com.gxx.nqh.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GXX on 2016/4/8.
 */
@Service
public class FriendshipServiceImpl implements FriendshipService {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public List<FriendDto> getFriendshipList(Long userId) {
        List<Friendship> friendshipList = (List<Friendship>) hibernateTemplate.find("from Friendship fs where fs.userId = ?", userId);
        List<FriendDto> friendDtoList = new ArrayList<FriendDto>(friendshipList.size());
        for (int i = 0; i < friendshipList.size(); i++) {
            FriendDto friendDto = new FriendDto();
            UserInfo frinendUserInfo = hibernateTemplate.get(UserInfo.class, friendshipList.get(i).getFriendId());
            friendDto.setFriendId(friendshipList.get(i).getFriendId());
            friendDto.setRealName(frinendUserInfo.getRealname());
            friendDtoList.add(friendDto);
        }
        return friendDtoList;
    }
}

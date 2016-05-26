package com.gxx.nqh.service;

import com.gxx.nqh.dto.FriendDto;

import java.util.List;

/**
 * Created by GXX on 2016/4/8.
 */
public interface FriendshipService {
    List<FriendDto> getFriendshipList(Long userId);
}

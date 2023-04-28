package com.project.gabojago.gabojagouser.service.my;

import com.project.gabojago.gabojagouser.dto.my.FollowDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;

import java.util.List;

public interface FollowService {
    int remove(FollowDto followDto);
    int register(FollowDto followDto);

    List<UserDto> followingList(String uId);
    List<UserDto> followerList(String uId);

}

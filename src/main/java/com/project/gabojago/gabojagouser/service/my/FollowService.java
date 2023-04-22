package com.project.gabojago.gabojagouser.service.my;

import com.project.gabojago.gabojagouser.dto.follow.FollowDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;

import java.util.List;

public interface FollowService {
    int remove(FollowDto followDto);
    int register(FollowDto followDto);
}

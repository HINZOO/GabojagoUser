package com.project.gabojago.gabojagouser.service.my;

import com.project.gabojago.gabojagouser.dto.my.FollowDto;

public interface FollowService {
    int remove(FollowDto followDto);
    int register(FollowDto followDto);
}

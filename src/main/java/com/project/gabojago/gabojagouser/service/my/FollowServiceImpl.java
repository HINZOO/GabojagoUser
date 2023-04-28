package com.project.gabojago.gabojagouser.service.my;

import com.project.gabojago.gabojagouser.dto.my.FollowDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.mapper.my.FollowMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FollowServiceImpl implements FollowService {
    private FollowMapper followMapper;


    @Override
    public int remove(FollowDto followDto) {
        int remove=followMapper.deleteByFromIdAndToId(followDto);
        return remove;
    }

    @Override
    public int register(FollowDto followDto) {
        int register= followMapper.insertOne(followDto);
        return register;
    }

    @Override
    public List<UserDto> followingList(String uId) {
        return followMapper.findByFromId(uId);
    }

    @Override
    public List<UserDto> followerList(String uId) {
        return followMapper.findByToId(uId);
    }


}

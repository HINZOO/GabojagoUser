package com.project.gabojago.gabojagouser.mapper.my;

import com.project.gabojago.gabojagouser.dto.follow.FollowDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowMapper {
    List<UserDto> findByFromId(String uId);
    List<UserDto> findByToId(String uId);
    int deleteByFromIdAndToId(FollowDto followDto);
    int insertOne(FollowDto followDto);
    boolean findByToIdAndFromIdIsLoginUserId(String uId);


}

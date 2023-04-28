package com.project.gabojago.gabojagouser.mapper.my;

import com.project.gabojago.gabojagouser.dto.my.FollowDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowMapper {
    List<UserDto> findByFromId(String uId);//팔로잉리스트
    List<UserDto> findByToId(String uId);//팔로워리스트
    int deleteByFromIdAndToId(FollowDto followDto);
    int insertOne(FollowDto followDto);
    boolean findByToIdAndFromIdIsLoginUserId(String uId);


}

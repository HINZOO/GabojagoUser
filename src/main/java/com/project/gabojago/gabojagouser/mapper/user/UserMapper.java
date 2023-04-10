package com.project.gabojago.gabojagouser.mapper.user;

import com.project.gabojago.gabojagouser.dto.user.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
  UserDto findUserByUIdAndPw(UserDto user);
}

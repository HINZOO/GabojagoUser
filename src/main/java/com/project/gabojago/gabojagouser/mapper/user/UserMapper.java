package com.project.gabojago.gabojagouser.mapper.user;

import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
  List<UserDto> findAll();
  UserDto findUserByUIdAndPw(UserDto user);
  UserDto findUserByUId(String user);
  int insertOne(UserDto user);
  int updateOne(UserDto user);
}

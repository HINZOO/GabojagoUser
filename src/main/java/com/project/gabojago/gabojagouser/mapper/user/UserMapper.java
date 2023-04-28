package com.project.gabojago.gabojagouser.mapper.user;

import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
  List<UserDto> findAll();
  UserDto findUserByUIdAndPw(String uId, String pw);
  UserDto findUserByUId(String user);
  int insertOne(UserDto user);
  int updateOne(UserDto user);

  int updatePwByUId(UserDto user);
  int setLoginUserId(String uId);
  int setLoginUserIdNull();
  int deleteOne(UserDto user);
  int updateStatusByUIdAndEmailCheckCode(UserDto user);//이메일 체크

  UserDto findUserByUIdAndPw(UserDto user);
}

package com.project.gabojago.gabojagouser.service.user;

import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.mapper.user.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  private UserMapper userMapper;
  public UserServiceImpl(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @Override
  public List<UserDto> list() {
    return this.userMapper.findAll();
  }
  @Override
  public UserDto login(UserDto user) {
    return userMapper.findUserByUIdAndPw(user);
  }

  @Override
  public UserDto detail(UserDto user) {
    return null;
  }

  @Override
  public UserDto idCheck(String user) {
    return userMapper.findUserByUId(user);
  }

  @Override
  public int modify(UserDto user) {
    return 0;
  }

  @Override
  public int signup(UserDto user) {
    return userMapper.insertOne(user);
  }

  @Override
  public int dropout(UserDto user) {
    return 0;
  }
}

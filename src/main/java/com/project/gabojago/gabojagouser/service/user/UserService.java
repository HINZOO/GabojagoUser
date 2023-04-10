package com.project.gabojago.gabojagouser.service.user;

import com.project.gabojago.gabojagouser.dto.user.UserDto;

import java.util.List;

public interface UserService {
  UserDto login(UserDto user);
  UserDto logout(UserDto user);
  int modify(UserDto user);
  int signup(UserDto user);
  int dropout(UserDto user);
}

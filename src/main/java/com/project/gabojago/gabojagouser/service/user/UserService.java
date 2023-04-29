package com.project.gabojago.gabojagouser.service.user;
import com.project.gabojago.gabojagouser.dto.user.UserDto;

import java.util.List;

public interface UserService {
  List<UserDto> list();
  UserDto login(UserDto user);
  UserDto detail(String user,String loginUserId);
  UserDto idCheck(String userId);

  int modify(UserDto user);
  int modifyIdANDPw(UserDto user);
  int signup(UserDto user);
  int dropout(UserDto user);
  int modifyEmailCheck(UserDto userDto);
}

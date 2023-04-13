package com.project.gabojago.gabojagouser.mapper.user;

import com.project.gabojago.gabojagouser.dto.user.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

  @Autowired
  private UserMapper userMapper;
  @Test
  void findUserByUIdAndPw() {
    UserDto user=new UserDto();
    user.setUId("USER01");
    user.setPw("1234");
    userMapper.findUserByUIdAndPw(user);
  }

}
package com.project.gabojago.gabojagouser.mapper.my;

import com.project.gabojago.gabojagouser.dto.user.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class FollowMapperTest {
@Autowired
FollowMapper followMapper;
    @Test
    void findAllByUId() {
        UserDto user = new UserDto();
        user.setUId("user01");

    }
}
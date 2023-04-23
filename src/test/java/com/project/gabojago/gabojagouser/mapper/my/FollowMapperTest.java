package com.project.gabojago.gabojagouser.mapper.my;

import com.project.gabojago.gabojagouser.dto.user.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

    @Test
    void findByFromId() {
        List<UserDto> followings=followMapper.findByFromId("user01");
        Assertions.assertNotNull(followings);
    }

    @Test
    void findByToId() {
        List<UserDto> followings=followMapper.findByToId("user01");
        Assertions.assertNotNull(followings);
    }

    @Test
    void deleteByFromIdAndToId() {
    }

    @Test
    void insertOne() {
    }

    @Test
    void findByToIdAndFromIdIsLoginUserId() {
    }
}
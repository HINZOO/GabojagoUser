package com.project.gabojago.gabojagouser.service.comm;

import com.project.gabojago.gabojagouser.dto.user.UserDto;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CommunityServiceImplTest {
    @Autowired
    CommunityService communityService;

    @Test
    void list() {
    }

    @Test
    void detail() {
        UserDto user = new UserDto();
        user.setUId("user01");
        communityService.detail(2,user);
    }
}
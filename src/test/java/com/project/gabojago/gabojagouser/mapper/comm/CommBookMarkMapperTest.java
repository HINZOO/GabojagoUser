package com.project.gabojago.gabojagouser.mapper.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommBookmarkDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CommBookMarkMapperTest {
    @Autowired
    CommBookMarkMapper commBookMarkMapper;
    @Test
    void findByUId() {
        commBookMarkMapper.findByUId("user01");
    }

    @Test
    void insertOne() {
        CommBookmarkDto commBookmarkDto=new CommBookmarkDto();
        commBookmarkDto.setCId(2);
        commBookmarkDto.setUId("user02");
        int insert=commBookMarkMapper.insertOne(commBookmarkDto);
        Assertions.assertEquals(insert,1);
    }

    @Test
    void deleteOne() {
    }
}
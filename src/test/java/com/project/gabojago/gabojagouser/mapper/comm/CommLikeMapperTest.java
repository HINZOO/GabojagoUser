package com.project.gabojago.gabojagouser.mapper.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommLikeDto;
import com.project.gabojago.gabojagouser.dto.comm.LikeStatusCntDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CommLikeMapperTest {
    @Autowired
    CommLikeMapper commLikeMapper;
    @Test
    void findByCIdAndUId() {
    }

    @Test
    void findByCIdAndUIdIsLoginUserId() {
    }

    @Test
    void countStatusByCId() {
    }

    @Test
    void countStatusByUId() {
    LikeStatusCntDto contUId=commLikeMapper.countStatusByUId("user01");
    System.out.println("contUId = " + contUId);
    }

    @Test
    void insertOne() {
        CommLikeDto commLikeDto= new CommLikeDto();
        commLikeDto.setCId(2);
        commLikeDto.setUId("user01");
        int insert=commLikeMapper.insertOne(commLikeDto);
        System.out.println("insert = " + insert);
        System.out.println("commLikeDto = " + commLikeDto);
    }

    @Test
    void deleteOne() {
    }
}
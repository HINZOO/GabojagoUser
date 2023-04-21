package com.project.gabojago.gabojagouser.mapper.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommLikeDto;
import com.project.gabojago.gabojagouser.dto.comm.LikeStatusCntDto;
import com.project.gabojago.gabojagouser.mapper.user.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CommLikeMapperTest {
    @Autowired
    CommLikeMapper commLikeMapper;
    @Autowired
    UserMapper userMapper;
    @Test
    void findByCIdAndUId() { //해당 유저가 2번글에 좋아요를 했는지 t/f
        boolean commLike= commLikeMapper.findByCIdAndUId(2,"user01");
        System.out.println("commLike = " + commLike);
    }

    @Test
    void findByCIdAndUIdIsLoginUserId() { //로그인한 유저가 2번 글에 좋아요 했는지 t/f
        userMapper.setLoginUserId("user01");
        boolean commLike=commLikeMapper.findByCIdAndUIdIsLoginUserId(2);
        userMapper.setLoginUserIdNull();
        System.out.println("commLike = " + commLike);

    }

    @Test
    void countStatusByCId() {
        LikeStatusCntDto likeStatusCntDto=new LikeStatusCntDto();
        likeStatusCntDto=commLikeMapper.countStatusByCId(2);
        System.out.println("likeStatusCntDto = " + likeStatusCntDto);
    }

    @Test
    void countStatusByUId() {
        LikeStatusCntDto likeStatusCntDto= commLikeMapper.countStatusByUId("user02");
        System.out.println(likeStatusCntDto);
    }

    @Test
    void insertOne() {
        CommLikeDto commLikeDto= new CommLikeDto();
        commLikeDto.setCId(2);
        commLikeDto.setUId("user03");
        int insert=commLikeMapper.insertOne(commLikeDto);
        System.out.println("insert = " + insert);
        System.out.println("commLikeDto = " + commLikeDto);
    }

    @Test
    void deleteOne() {
        CommLikeDto commLikeDto=new CommLikeDto();
        commLikeDto.setCId(2);
        commLikeDto.setUId("user03");
        commLikeMapper.deleteOne(commLikeDto);
    }
}
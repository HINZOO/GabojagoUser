package com.project.gabojago.gabojagouser.mapper.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommPageDto;
import com.project.gabojago.gabojagouser.dto.comm.CommunityDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommunityMapperTest {
    @Autowired
    CommunityMapper communityMapper;

    @Test
    void findAll() {
        CommPageDto pageDto=new CommPageDto();
        List<CommunityDto> list=communityMapper.findAll(pageDto);
        System.out.println(list);
        Assertions.assertNotNull(list);
    }

    @Test
    void findByCId() {
        CommunityDto detail=communityMapper.findByCId(2);
        System.out.println(detail);
        Assertions.assertNotNull(detail);
    }

    @Test
    void insertOne() {
        CommunityDto comm= new CommunityDto();
        comm.setUId("USER03");
        comm.setPId(2);
        comm.setTitle("테스트글");
        comm.setContent("테스트내용입니다.");
        comm.setArea("인천");


        int insert=communityMapper.insertOne(comm);
        Assertions.assertEquals(insert,1);
    }

    @Test
    void updateOne() {
        CommunityDto comm=new CommunityDto();
        comm.setCId(3);
        comm.setPId(1);
        comm.setTitle("수정테스트글");
        comm.setContent("수정테스트내용입니다.");
        comm.setArea("경기");

        int update=communityMapper.updateOne(comm);
        System.out.println(comm);
        System.out.println("update = " + update);
        Assertions.assertEquals(update,1);
    }

    @Test
    void deleteOne() {
        int delete=communityMapper.deleteOne(3);
        System.out.println("delete = " + delete);
        Assertions.assertEquals(delete,1);
    }
}
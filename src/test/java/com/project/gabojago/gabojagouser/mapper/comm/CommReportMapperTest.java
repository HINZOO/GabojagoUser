package com.project.gabojago.gabojagouser.mapper.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommReportDto;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CommReportMapperTest {
    @Autowired
    CommReportMapper commReportMapper;
    @Test
    void findAll() {
        commReportMapper.findAll();
    }

    @Test
    void findByCrId() {

        CommReportDto commReportDto=commReportMapper.findByCrId(1);
        System.out.println(commReportDto);
    }

    @Test
    void insertOne() {
        CommReportDto commReportDto=new CommReportDto();
        commReportDto.setCId(2);
        commReportDto.setUId("user02");
        commReportDto.setContent("신고테스트2");
        int insert=commReportMapper.insertOne(commReportDto);
        System.out.println("commReportDto = " + commReportDto);
    }

    @Test
    void deleteOne() {
    }
}
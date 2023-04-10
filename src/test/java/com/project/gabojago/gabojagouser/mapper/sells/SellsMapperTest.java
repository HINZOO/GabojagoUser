package com.project.gabojago.gabojagouser.mapper.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SellsMapperTest {

    @Autowired
    private SellsMapper sellsMapper;
    @Test
    void findAll() {
        List<SellsDto> findAll=sellsMapper.findAll();
        System.out.println("findAll = " + findAll);
    }

    @Test
    void findBySId() {
        SellsDto sells=sellsMapper.findBySId(7);
        System.out.println("sells = " + sells);
    }

    @Test
    void insertOne() {

        SellsDto sell=new SellsDto();
        sell.setUId("user04");
        sell.setOId(1);
        sell.setArea("인천");
        sell.setTitle("인천 여행");
        sell.setContent("인천 도시 여행");
        sell.setCategory("테마");
        int insert=sellsMapper.insertOne(sell);
        System.out.println("insert = " + insert);

    }

    @Test
    void updateOne() {
        SellsDto sell=new SellsDto();
        sell.setSId(7);
        sell.setArea("대전");
        sell.setTitle("대전");
        sell.setContent("대전 여행 !!!");
        sell.setCategory("워터");
        int update= sellsMapper.updateOne(sell);
        System.out.println("update = " + update);

    }

    @Test
    void deleteOne() {
    }

}
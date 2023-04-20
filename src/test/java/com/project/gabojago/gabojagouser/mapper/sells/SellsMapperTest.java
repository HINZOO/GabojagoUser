package com.project.gabojago.gabojagouser.mapper.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellPageDto;
import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SellsMapperTest {

    @Autowired
    private SellsMapper sellsMapper;
    @Test
    void findAll() {
        SellPageDto pageDto=new SellPageDto();
        List<SellsDto> findAll=sellsMapper.findAll(pageDto);
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
        sell.setArea("인천");
        sell.setTitle("인천 여행1112");
        sell.setContent("인천 도시 여행");
        sell.setCategory("워터");
        int insert=sellsMapper.insertOne(sell);
        System.out.println("insert = " + insert);
        System.out.println("insert = " + sell.getSId());

    }

    @Test
    void updateOne() {
        SellsDto sell=new SellsDto();
        sell.setSId(7);
        sell.setArea("대전");
        sell.setTitle("대전");
        sell.setContent("대전 여행 성공!!!");
        sell.setCategory("워터");
        int update= sellsMapper.updateOne(sell);
        System.out.println("update = " + update);

    }

    @Test
    void deleteOne() {
        int delete=sellsMapper.deleteOne(7);
        System.out.println("delete = " + delete);
    }

    @Test
    void findByCategory() {
        SellPageDto pageDto=new SellPageDto();
        List<SellsDto> sellsCategory=sellsMapper.findByCategory("레저", pageDto);
        System.out.println("sellsCategory = " + sellsCategory);
    }
}
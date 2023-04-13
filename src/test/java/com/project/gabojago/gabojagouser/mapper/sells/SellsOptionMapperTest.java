package com.project.gabojago.gabojagouser.mapper.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellsOptionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SellsOptionMapperTest {
    @Autowired
    private SellsOptionMapper sellsOptionMapper;

    @Test
    void findBySId() {
        List<SellsOptionDto> sellsOptionDtos=sellsOptionMapper.findBySId(1);
        System.out.println("sellsOptionDtos = " + sellsOptionDtos);
    }

    @Test
    void insertOne() {
        SellsOptionDto sellsOption=new SellsOptionDto();
        sellsOption.setSId(1);
        sellsOption.setName("영유아");
        sellsOption.setPrice(2000);
        sellsOption.setStock(100);
        int insert=sellsOptionMapper.insertOne(sellsOption);
        System.out.println("insert = " + insert);

    }

    @Test
    void updateOne() {
        SellsOptionDto sellsOption=new SellsOptionDto();
        sellsOption.setName("영아");
        sellsOption.setPrice(2500);
        sellsOption.setStock(50);
        sellsOption.setOId(4);
        int update=sellsOptionMapper.updateOne(sellsOption);
        System.out.println("update = " + update);
    }

    @Test
    void deleteOne() {
        int delete=sellsOptionMapper.deleteOne(4);
        System.out.println("delete = " + delete);
    }
}
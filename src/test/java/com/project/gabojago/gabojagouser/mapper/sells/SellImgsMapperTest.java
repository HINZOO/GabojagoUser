package com.project.gabojago.gabojagouser.mapper.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellImgsDto;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SellImgsMapperTest {
    @Autowired
    private SellImgsMapper sellImgsMapper;
    @Test
    void findBySId() {
        List<SellImgsDto> sellimgs = sellImgsMapper.findBySId(27);
        System.out.println("sellimgs = " + sellimgs);
        assertNotNull(sellimgs);
    }

    @Test
    void insertOne() {
        SellImgsDto sellImg=new SellImgsDto();
        sellImg.setSId(85);
        sellImg.setImgPath("/public/img/sells/테마3.png");
        int register=sellImgsMapper.insertOne(sellImg);
        assertEquals(register,1);
    }

    @Test
    void deleteOne() {
        int delete=sellImgsMapper.deleteOne(3);
        assertEquals(delete,1);
    }
}
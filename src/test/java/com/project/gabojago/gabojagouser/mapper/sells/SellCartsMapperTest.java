package com.project.gabojago.gabojagouser.mapper.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellCartDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SellCartsMapperTest {
    @Autowired
    private SellCartsMapper sellCartsMapper;
    @Test
    void findByUId() {
        List<SellCartDto> LIST = sellCartsMapper.findByUId("USER01");
        System.out.println("LIST = " + LIST);

    }

    @Test
    void insertOne() {
    }

    @Test
    void deleteOne() {
    }
}
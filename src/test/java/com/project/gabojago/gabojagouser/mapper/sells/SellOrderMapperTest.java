package com.project.gabojago.gabojagouser.mapper.sells;

import com.project.gabojago.gabojagouser.service.sells.SellOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SellOrderMapperTest {
    @Autowired
    private SellOrderService sellOrderService;
    @Test
    void findByUId() {
        sellOrderService.findByUId("user10");
    }

    @Test
    void insertOne() {
    }
}
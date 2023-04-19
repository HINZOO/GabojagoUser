package com.project.gabojago.gabojagouser.service.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import com.project.gabojago.gabojagouser.mapper.sells.SellsMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SellsServiceTest {
@Autowired
    private SellsMapper sellsMapper;
    @Test
    void findByTitle() {
        List<SellsDto> title = sellsMapper.findByTitle("테마");
        System.out.println("title = " + title);
    }
}
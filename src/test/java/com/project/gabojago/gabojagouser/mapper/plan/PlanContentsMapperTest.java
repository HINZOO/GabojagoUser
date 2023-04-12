package com.project.gabojago.gabojagouser.mapper.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanContentsDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlanContentsMapperTest {

    @Autowired
    private PlanContentsMapper planContentsMapper;
    @Test
    void insertOne() {
        PlanContentsDto planContentsDto = new PlanContentsDto();
        planContentsDto.setPId(1);
        planContentsDto.setSId(1);
        planContentsDto.setTId(1);
        planContentsDto.setTitle("123");
        int i = planContentsMapper.insertOne(planContentsDto);
        System.out.println(i);
    }
}

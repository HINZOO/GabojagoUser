package com.project.gabojago.gabojagouser.mapper.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlanMapperTest {
    @Autowired
    private PlanMapper planMapper;
    @Test
    void findAll() {

        List<PlanDto> planDto = planMapper.findAll();
        System.out.println("planDto = " + planDto);
    }
}

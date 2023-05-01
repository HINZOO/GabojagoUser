package com.project.gabojago.gabojagouser.mapper.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanDto;
import org.junit.jupiter.api.Assertions;
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

    @Test
    void findByPId(){
        PlanDto planDto = planMapper.findByPId(1);
        Assertions.assertNotNull(planDto);
    }

    @Test
    void insertOne(){
        PlanDto planDto = new PlanDto();
        planDto.setUId("USER01");
        planDto.setTitle("테스트 플랜");
        planDto.setPlanFrom("2023-01-01");
        planDto.setPlanTo("2023-01-02");
        int insert = planMapper.insertOne(planDto);
        Assertions.assertEquals(1,insert);
    }

    @Test
    void deleteOne(){
        int delete = planMapper.deleteOne(7);
        Assertions.assertEquals(1,delete);
    }
}

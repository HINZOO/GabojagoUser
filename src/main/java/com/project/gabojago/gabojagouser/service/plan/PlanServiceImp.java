package com.project.gabojago.gabojagouser.service.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanDto;
import com.project.gabojago.gabojagouser.mapper.plan.PlanMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlanServiceImp implements PlanService {

    private PlanMapper planMapper;
    @Override
    public List<PlanDto> list() {
        return null;
    }

    @Override
    public PlanDto detail(int pId) {
        return null;
    }

    @Override
    public int register(PlanDto plan) {
        return planMapper.insertOne(plan);
    }

    @Override
    public int modify(PlanDto plan) {
        return 0;
    }

    @Override
    public int remove(int pId) {
        return 0;
    }
}

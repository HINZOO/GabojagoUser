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
    public List<PlanDto> list(String uId) {
        return planMapper.findByUId(uId);
    }

    @Override
    public PlanDto detail(int pId) {
        return planMapper.findByPId(pId);
    }

    @Override
    public int register(PlanDto plandto) {
        return planMapper.insertOne(plandto);
    }

    @Override
    public int modify(PlanDto plandto) {
        return planMapper.updateOne(plandto);
    }

    @Override
    public int remove(int pId) {
        return planMapper.deleteOne(pId);
    }
}

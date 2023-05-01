package com.project.gabojago.gabojagouser.service.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanContentsDto;
import com.project.gabojago.gabojagouser.mapper.plan.PlanContentsMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlanContentsServiceImp implements PlanContentsService {

    private PlanContentsMapper planContentsMapper;
    @Override
    public int register(PlanContentsDto planContentsDto) {
        return planContentsMapper.insertOne(planContentsDto);
    }

    @Override
    public PlanContentsDto detail(int conId) {
        return planContentsMapper.findByConId(conId);
    }

    @Override
    public int remove(int conId) {
        return planContentsMapper.deleteOne(conId);
    }

    @Override
    public int modify(PlanContentsDto planContentsDto) {
        return planContentsMapper.updateOne(planContentsDto);
    }

    @Override
    public int updateImg(PlanContentsDto planContentsDto) {
        return planContentsMapper.updateImg(planContentsDto);
    }
}

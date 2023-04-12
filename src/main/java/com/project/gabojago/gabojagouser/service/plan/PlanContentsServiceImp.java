package com.project.gabojago.gabojagouser.service.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanContentsDto;
import com.project.gabojago.gabojagouser.mapper.plan.PlanContentsMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlanContentsServiceImp implements PlanContentsService {

    PlanContentsMapper planContentsMapper;
    @Override
    public int register(PlanContentsDto planContentsDto) {
        return planContentsMapper.insertOne(planContentsDto);
    }
}

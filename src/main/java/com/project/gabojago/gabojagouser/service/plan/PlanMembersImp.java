package com.project.gabojago.gabojagouser.service.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanMembersDto;
import com.project.gabojago.gabojagouser.mapper.plan.PlanMembersMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlanMembersImp implements PlanMembersService {

    private PlanMembersMapper planMembersMapper;
    @Override
    public int register(PlanMembersDto planMembersDto) {
        return planMembersMapper.insertOne(planMembersDto);
    }
}

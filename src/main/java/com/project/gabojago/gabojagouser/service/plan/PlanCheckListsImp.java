package com.project.gabojago.gabojagouser.service.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanCheckListsDto;
import com.project.gabojago.gabojagouser.dto.plan.PlanContentPathsDto;
import com.project.gabojago.gabojagouser.mapper.plan.PlanCheckListsMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlanCheckListsImp implements PlanCheckListsService{
    PlanCheckListsMapper planCheckListsMapper;
    @Override
    public int register(PlanCheckListsDto planCheckListsDto) {
        return planCheckListsMapper.insertOne(planCheckListsDto);
    }

    @Override
    public int modify(PlanCheckListsDto planCheckListsDto) {
        return planCheckListsMapper.updateCheckStatus(planCheckListsDto);
    }

    @Override
    public int remove(int clId) {
        return planCheckListsMapper.deleteOne(clId);
    }
}

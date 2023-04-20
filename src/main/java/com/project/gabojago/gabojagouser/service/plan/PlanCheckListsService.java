package com.project.gabojago.gabojagouser.service.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanCheckListsDto;
import com.project.gabojago.gabojagouser.dto.plan.PlanContentPathsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;



public interface PlanCheckListsService {
    int register(PlanCheckListsDto planCheckListsDto);
    int modify(PlanCheckListsDto planCheckListsDto);
    int remove(int clId);
}

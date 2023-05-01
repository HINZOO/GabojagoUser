package com.project.gabojago.gabojagouser.service.plan;


import com.project.gabojago.gabojagouser.dto.plan.PlanContentsDto;

public interface PlanContentsService {
    int register(PlanContentsDto planContentsDto);
    PlanContentsDto detail(int conId);
    int remove (int conId);
    int modify (PlanContentsDto planContentsDto);
    int updateImg (PlanContentsDto planContentsDto);

}

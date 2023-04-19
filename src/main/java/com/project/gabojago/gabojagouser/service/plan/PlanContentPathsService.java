package com.project.gabojago.gabojagouser.service.plan;


import com.project.gabojago.gabojagouser.dto.plan.PlanContentPathsDto;

public interface PlanContentPathsService {
    int register(PlanContentPathsDto conId);
    int delete(int pathId);
}

package com.project.gabojago.gabojagouser.service.plan;


import com.project.gabojago.gabojagouser.dto.plan.PlanContentPathsDto;

public interface PlanContentPathsService {

    PlanContentPathsDto detail(int pathId);
    int updatePath(PlanContentPathsDto pathsDto);
    int register(PlanContentPathsDto planContentPathsDto);
    int delete(int pathId);

}

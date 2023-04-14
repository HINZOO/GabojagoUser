package com.project.gabojago.gabojagouser.mapper.plan;



import com.project.gabojago.gabojagouser.dto.plan.PlanContentPathsDto;

import java.util.List;

public interface PlanContentPathsMapper {
    PlanContentPathsDto findByConId(int conId);
}

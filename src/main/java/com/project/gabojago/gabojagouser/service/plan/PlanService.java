package com.project.gabojago.gabojagouser.service.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanDto;

import java.util.List;

public interface PlanService {
    List<PlanDto> list();

    PlanDto detail(int pId);
    int register(PlanDto plan);
    int modify(PlanDto plan);
    int remove(int pId);
}

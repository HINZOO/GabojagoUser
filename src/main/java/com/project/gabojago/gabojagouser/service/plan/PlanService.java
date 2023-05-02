package com.project.gabojago.gabojagouser.service.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanDto;
import com.project.gabojago.gabojagouser.dto.plan.PlanPageDto;

import java.util.List;

public interface PlanService {
    List<PlanDto> list(String uId);
    List<PlanDto> list(String uId, PlanPageDto pageDto);
    List<PlanDto> bookmarkedList(String uId);
    PlanDto detail(int pId);
    int register(PlanDto plan);
    int modify(PlanDto plan);
    int remove(int pId);
}

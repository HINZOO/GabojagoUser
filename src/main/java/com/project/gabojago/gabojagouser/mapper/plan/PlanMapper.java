package com.project.gabojago.gabojagouser.mapper.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanMapper {
    List<PlanDto> findAll();
}

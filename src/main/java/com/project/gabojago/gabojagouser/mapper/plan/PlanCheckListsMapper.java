package com.project.gabojago.gabojagouser.mapper.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanCheckListsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanCheckListsMapper {
    List<PlanCheckListsDto> findByPId(int pId);
    int insertOne(PlanCheckListsDto planCheckListsDto);
    int updateCheckStatus(PlanCheckListsDto planCheckListsDto);
    int deleteOne(int clId);
}

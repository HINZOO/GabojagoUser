package com.project.gabojago.gabojagouser.mapper.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanMembersDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanMembersMapper {
    List<PlanMembersDto> findByPId(int pId);
    int insertOne(PlanMembersDto planMembersDto);
}

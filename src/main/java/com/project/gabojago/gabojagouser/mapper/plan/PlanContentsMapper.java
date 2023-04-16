package com.project.gabojago.gabojagouser.mapper.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanContentsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanContentsMapper {
    List<PlanContentsDto> findAll();
//    List<PlanContentsDto> findByUId(String uId);
//    PlanDto findByPId(int pId);
    List<PlanContentsDto> findByPId(int pId);
    int insertOne(PlanContentsDto planContent);
    int updateOne(PlanContentsDto planContent);
    int deleteOne(PlanContentsDto conId);
}

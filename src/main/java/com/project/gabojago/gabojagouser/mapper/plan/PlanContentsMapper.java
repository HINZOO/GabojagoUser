package com.project.gabojago.gabojagouser.mapper.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanContentsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanContentsMapper {
    List<PlanContentsDto> findAll();
    List<PlanContentsDto> findByPId(int pId);
    PlanContentsDto findByConId(int conId);
    int insertOne(PlanContentsDto planContent);
    int updateOne(PlanContentsDto planContent);
    int updateImg(PlanContentsDto planContent);
    int deleteOne(int conId);
}

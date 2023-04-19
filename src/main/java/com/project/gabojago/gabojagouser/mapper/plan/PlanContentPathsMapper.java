package com.project.gabojago.gabojagouser.mapper.plan;



import com.project.gabojago.gabojagouser.dto.plan.PlanContentPathsDto;
import com.project.gabojago.gabojagouser.dto.plan.PlanContentsDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanContentPathsMapper {

    int insert(PlanContentPathsDto conId);
//    int updateOne(PlanContentPathsDto planContentPathsDto);
    int deleteOne(int pathId);
}

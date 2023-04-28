package com.project.gabojago.gabojagouser.mapper.plan;



import com.project.gabojago.gabojagouser.dto.plan.PlanContentPathsDto;
import com.project.gabojago.gabojagouser.dto.plan.PlanContentsDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanContentPathsMapper {

    PlanContentPathsDto findByPathId(int pathId);
    int insert(PlanContentPathsDto pathsDto);
    int updateOne(PlanContentPathsDto pathsDto);
    int deleteOne(int pathId);
}

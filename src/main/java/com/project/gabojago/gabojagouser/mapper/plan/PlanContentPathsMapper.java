package com.project.gabojago.gabojagouser.mapper.plan;



import com.project.gabojago.gabojagouser.dto.plan.PlanContentPathsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface PlanContentPathsMapper {

    int insert(int conId);
}

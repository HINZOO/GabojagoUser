package com.project.gabojago.gabojagouser.service.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanContentPathsDto;
import com.project.gabojago.gabojagouser.mapper.plan.PlanContentPathsMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlanContentPathsImp implements PlanContentPathsService {
    private PlanContentPathsMapper planContentPathsMapper;

    @Override
    public PlanContentPathsDto detail(int pathId) {
        return planContentPathsMapper.findByPathId(pathId);
    }

    @Override
    public int updatePath(PlanContentPathsDto pathsDto) {
        return planContentPathsMapper.updateOne(pathsDto);
    }

    @Override
    public int register(PlanContentPathsDto pathsDto) {
        return planContentPathsMapper.insert(pathsDto);
    }

    @Override
    public int delete(int pathId) {
        return planContentPathsMapper.deleteOne(pathId);
    }
}

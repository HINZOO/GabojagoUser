package com.project.gabojago.gabojagouser.service.plan;

import com.github.pagehelper.PageHelper;
import com.project.gabojago.gabojagouser.dto.plan.PlanDto;
import com.project.gabojago.gabojagouser.dto.plan.PlanPageDto;
import com.project.gabojago.gabojagouser.mapper.plan.PlanMapper;
import com.project.gabojago.gabojagouser.mapper.user.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import java.util.List;

@Service
@AllArgsConstructor
public class PlanServiceImp implements PlanService {

    private PlanMapper planMapper;
    private UserMapper userMapper;

//    필요없어짐
    @Override
    public List<PlanDto> list(String uId) {
        userMapper.setLoginUserId(uId);
        List<PlanDto> list = planMapper.findByUId();
        userMapper.setLoginUserIdNull();
        return list;
    }

    @Override
    public List<PlanDto> list(String uId, PlanPageDto pageDto) {
        userMapper.setLoginUserId(uId);
        PageHelper.startPage(pageDto.getPageNum(),pageDto.getPageSize(),pageDto.getOrderBy());
        List<PlanDto> list = planMapper.findByUId(pageDto);
        userMapper.setLoginUserIdNull();
        return list;
    }

    @Override
    public List<PlanDto> bookmarkedList(String uId) {
        return planMapper.findByBookmarked(uId);
    }

    @Override
    public PlanDto detail(int pId) {
        return planMapper.findByPId(pId);
    }

    @Override
    public int register(PlanDto plandto) {
        return planMapper.insertOne(plandto);
    }

    @Override
    public int modify(PlanDto plandto) {
        return planMapper.updateOne(plandto);
    }

    @Override
    public int remove(int pId) {
        return planMapper.deleteOne(pId);
    }
}

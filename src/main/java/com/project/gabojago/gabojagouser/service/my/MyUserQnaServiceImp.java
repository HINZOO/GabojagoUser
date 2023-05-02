package com.project.gabojago.gabojagouser.service.my;

import com.github.pagehelper.PageHelper;
import com.project.gabojago.gabojagouser.dto.my.MyUserQnaDto;
import com.project.gabojago.gabojagouser.dto.my.MyUserQnaReplyDto;
import com.project.gabojago.gabojagouser.dto.my.QnaPageDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.mapper.my.MyUserQnaMapper;
import com.project.gabojago.gabojagouser.mapper.user.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor

public class MyUserQnaServiceImp implements MyUserQnaService {
    private MyUserQnaMapper myUserQnaMapper;
    private UserMapper userMapper;
    @Override
    public List<MyUserQnaDto> list() {
        List<MyUserQnaDto> list=myUserQnaMapper.findAll();
        return list;
    }

    @Override
    public List<MyUserQnaDto> list(String uId, QnaPageDto pageDto) {
        PageHelper.startPage(pageDto.getPageNum(),pageDto.getPageSize(),pageDto.getOrderBy());
        List<MyUserQnaDto> list=myUserQnaMapper.findByUId(uId);
        return list;
    }

    @Override
    public MyUserQnaDto detail(int qId, UserDto loginUser) {
        MyUserQnaDto detail=myUserQnaMapper.findByQId(qId);
        return detail;
    }


    @Transactional
    @Override
    public int register(MyUserQnaDto myUserQna) {
        int register=myUserQnaMapper.insertOne(myUserQna);
        return register;
    }

    @Transactional
    @Override
    public int modify(MyUserQnaDto myUserQna) {
        int modify=myUserQnaMapper.updateOne(myUserQna);
        return modify;
    }

    @Override
    public int remove(int qId) {
        int remove=myUserQnaMapper.deleteOne(qId);
        return remove;
    }
}

package com.project.gabojago.gabojagouser.service.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommLikeDto;
import com.project.gabojago.gabojagouser.dto.comm.LikeStatusCntDto;
import com.project.gabojago.gabojagouser.mapper.comm.CommLikeMapper;
import com.project.gabojago.gabojagouser.mapper.user.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommLikeServiceImpl implements CommLikeService{
    private CommLikeMapper commLikeMapper;
    private UserMapper userMapper;

    @Override
    public LikeStatusCntDto read(int cId) {
        LikeStatusCntDto read=commLikeMapper.countStatusByCId(cId);
        return read;
    }

    @Override
    public boolean detail(int cId, String uId) {
        boolean detail=commLikeMapper.findByCIdAndUId(cId,uId);
        return detail;
    }
    @Override
    public LikeStatusCntDto read(int cId, String loginUserId) {
        userMapper.setLoginUserId(loginUserId);
        LikeStatusCntDto read=commLikeMapper.countStatusByCId(cId);
        userMapper.setLoginUserIdNull();
        return read;
    }


    @Override
    public int register(CommLikeDto like) {
        int register=commLikeMapper.insertOne(like);
        return register;
    }


    @Override
    public int remove(CommLikeDto like) {
        int remove=commLikeMapper.deleteOne(like);
        return remove;
    }


}
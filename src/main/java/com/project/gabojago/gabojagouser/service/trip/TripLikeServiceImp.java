package com.project.gabojago.gabojagouser.service.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripLikeDto;
import com.project.gabojago.gabojagouser.dto.trip.TripLikeStatusCntDto;
import com.project.gabojago.gabojagouser.mapper.trip.TripLikeMapper;
import com.project.gabojago.gabojagouser.mapper.user.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TripLikeServiceImp implements TripLikeService {
    private TripLikeMapper tripLikeMapper;
    private UserMapper userMapper;

    @Override
    public TripLikeStatusCntDto read(int tId) {
        TripLikeStatusCntDto read=tripLikeMapper.countStatusByTId(tId);
        return read;
    }

    @Override
    public TripLikeStatusCntDto read(int tId, String loginUserId) {
        userMapper.setLoginUserId(loginUserId);
        TripLikeStatusCntDto read=tripLikeMapper.countStatusByTId(tId);
        userMapper.setLoginUserIdNull();
        return read;
    }

    @Override
    public boolean detail(int tId, String uId) {
        boolean detail=tripLikeMapper.findByTIdAndUId(tId,uId);
        return detail;
    }

    @Override
    public int register(TripLikeDto like) {
        int register=tripLikeMapper.insertOne(like);
        return register;
    }

    @Override
    public int remove(TripLikeDto like) {
        int remove=tripLikeMapper.deleteOne(like);
        return remove;
    }
}

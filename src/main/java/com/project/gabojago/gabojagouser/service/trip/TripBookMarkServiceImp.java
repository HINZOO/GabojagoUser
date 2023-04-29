package com.project.gabojago.gabojagouser.service.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripBookMarkCntDto;
import com.project.gabojago.gabojagouser.dto.trip.TripBookmarkDto;
import com.project.gabojago.gabojagouser.dto.trip.TripDto;
import com.project.gabojago.gabojagouser.mapper.trip.TripBookmarkMapper;
import com.project.gabojago.gabojagouser.mapper.user.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TripBookMarkServiceImp implements TripBookMarkService{
    private TripBookmarkMapper bookmarkMapper;
    private UserMapper userMapper;

    @Override
    public boolean detail(int tId, String uId) {
        boolean detail=bookmarkMapper.findByTIdAndUId(tId,uId);
        return detail;
    }

    @Override
    public TripBookMarkCntDto read(int tId) {
        TripBookMarkCntDto read=bookmarkMapper.countStatusByTId(tId);
        return read;
    }

    @Override
    public TripBookMarkCntDto read(int tId, String loginUserId) {
        userMapper.setLoginUserId(loginUserId);
        TripBookMarkCntDto read=bookmarkMapper.countStatusByTId(tId);
        userMapper.setLoginUserIdNull();
        return read;
    }

    @Override
    public List<TripBookmarkDto> list(String uId) {
        List<TripBookmarkDto> list=bookmarkMapper.findByUId(uId);
        return list;
    }

    @Override
    public List<TripDto> bookmarkedTripList(String uId) {
        List<TripDto> list = bookmarkMapper.findTripsByUId(uId);
        return list;
    }

    @Override
    public int register(TripBookmarkDto tripBookmarkDto) {
        int register=bookmarkMapper.insertOne(tripBookmarkDto);
        return register;
    }

    @Override
    public int remove(int tbId) {
        int remove=bookmarkMapper.deleteOne(tbId);
        return remove;
    }
}

package com.project.gabojago.gabojagouser.service.trip;

import com.github.pagehelper.PageHelper;
import com.project.gabojago.gabojagouser.dto.comm.CommunityDto;
import com.project.gabojago.gabojagouser.dto.trip.TripDto;
import com.project.gabojago.gabojagouser.dto.trip.TripImgDto;
import com.project.gabojago.gabojagouser.dto.trip.TripPageDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.mapper.trip.TripImgMapper;
import com.project.gabojago.gabojagouser.mapper.trip.TripMapper;
import com.project.gabojago.gabojagouser.mapper.user.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TripServiceImp implements TripService {
    private TripMapper tripMapper; // @AllArgsConstructor
    private TripImgMapper tripImgMapper;
    private UserMapper userMapper;

    @Override
    public List<TripImgDto> imgList(List<Integer> tiId) { // 이미지 리스트
        List<TripImgDto> imgList=null;
        if(tiId!=null){
            imgList=new ArrayList<>();
            for(int id : tiId){
                TripImgDto imgDto=tripImgMapper.findByTiId(id);
                imgList.add(imgDto);
            }
        }
        return imgList;
    }

    @Override
    public List<TripDto> list(UserDto loginUser, TripPageDto pageDto) { // 여행정보 리스트
        if(loginUser!=null) userMapper.setLoginUserId(loginUser.getUId());
        PageHelper.startPage(pageDto.getPageNum(),pageDto.getPageSize(),pageDto.getOrderBy());
        List<TripDto> list=tripMapper.findAll(pageDto);
        if(loginUser!=null)userMapper.setLoginUserIdNull();
        return list;
    }

    @Override
    public TripDto detail(int tId, UserDto loginUser) {
        if(loginUser!=null){
            userMapper.setLoginUserId(loginUser.getUId());
        }
        tripMapper.updateIncrementViewCountByTId(tId);
        TripDto detail=tripMapper.findByTId(tId);
        userMapper.setLoginUserIdNull();
        return detail;
    }

    @Override
    public int register(TripDto trip) {
        int register=0;
        register=tripMapper.insertOne(trip);
        if(trip.getImgs()!=null){ // img 가 null 이 아니면
            for(TripImgDto img : trip.getImgs()) {
                img.setTId(trip.getTId());
                register+=tripImgMapper.insertOne(img);
            }
        }
        return register;
    }

    @Override
    public int modify(TripDto trip, List<Integer> delImgIds) {
        int modify=tripMapper.updateOne(trip);
        if(trip.getImgs()!=null){
            for(TripImgDto img : trip.getImgs()){
                img.setTId(trip.getTId());
                modify+=tripImgMapper.insertOne(img);
            }
        }
        if(delImgIds!=null){
            for(int tiId : delImgIds){
                modify+=tripImgMapper.deleteOne(tiId);
            }
        }
        return modify;
    }



    @Override
    public int remove(int tId, List<TripImgDto> imgDtos) {
        int remove=tripMapper.deleteOne(tId);
        return remove;
    }

    @Override
    public List<TripDto> likesList(TripPageDto pageDto) {
        PageHelper.startPage(pageDto.getPageNum(),pageDto.getPageSize(),pageDto.getOrderBy());
        List<TripDto> likesList=tripMapper.countListBylikes(pageDto);
        return likesList;
    }
}

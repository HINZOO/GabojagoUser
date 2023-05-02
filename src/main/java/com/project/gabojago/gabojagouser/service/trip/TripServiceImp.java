package com.project.gabojago.gabojagouser.service.trip;

import com.github.pagehelper.PageHelper;
import com.project.gabojago.gabojagouser.dto.HashTagDto;
import com.project.gabojago.gabojagouser.dto.comm.CommunityDto;
import com.project.gabojago.gabojagouser.dto.trip.TripDto;
import com.project.gabojago.gabojagouser.dto.trip.TripHashTagDto;
import com.project.gabojago.gabojagouser.dto.trip.TripImgDto;
import com.project.gabojago.gabojagouser.dto.trip.TripPageDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.mapper.HashTagMapper;
import com.project.gabojago.gabojagouser.mapper.trip.TripHashTagMapper;
import com.project.gabojago.gabojagouser.mapper.trip.TripImgMapper;
import com.project.gabojago.gabojagouser.mapper.trip.TripMapper;
import com.project.gabojago.gabojagouser.mapper.user.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TripServiceImp implements TripService {
    private TripMapper tripMapper; // @AllArgsConstructor
    private TripImgMapper tripImgMapper;
    private UserMapper userMapper;
    private TripHashTagMapper tripHashTagMapper;
    private HashTagMapper hashTagMapper;
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
    public List<TripDto> list(String uId, TripPageDto pageDto) {
        List<TripDto> list=tripMapper.findByUId(uId);
        PageHelper.startPage(pageDto.getPageNum(),pageDto.getPageSize(),pageDto.getOrderBy());
        return list;
    }

    @Override
    public List<TripDto> tagList(String tag, UserDto loginUser, TripPageDto pageDto) {
        if(loginUser!=null) userMapper.setLoginUserId(loginUser.getUId());
        PageHelper.startPage(pageDto.getPageNum(),pageDto.getPageSize(),pageDto.getOrderBy());
        List<TripDto> tagList=tripMapper.findByTag(tag);
        if(loginUser!=null)userMapper.setLoginUserIdNull();
        return tagList;
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
    @Transactional
    public int register(TripDto trip, List<String> tags) {
        int register=0;
        register=tripMapper.insertOne(trip);
        if(trip.getImgs()!=null){ // img 가 null 이 아니면
            for(TripImgDto img : trip.getImgs()) {
                img.setTId(trip.getTId());
                register+=tripImgMapper.insertOne(img);
            }
        }
        if(tags!=null){
            for(String tag:tags){
                HashTagDto hashTag=hashTagMapper.findByTag(tag);
                if(hashTag==null) hashTagMapper.insertOne(tag);
                TripHashTagDto tripHashTag=new TripHashTagDto();
                tripHashTag.setTId(trip.getTId());
                tripHashTag.setTag(tag);
                tripHashTagMapper.insertOne(tripHashTag);
            }
        }
        return register;
    }

    @Override
    @Transactional
    public int modify(TripDto trip, List<Integer> delImgIds, List<String> tags, List<String> delTags) {
        // tag 는 객체니까 List, delImgIds 는 리스트 배열은 배열에 .add 등 컨트롤러에서 배열 추가하려고
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
        if(tags!=null){ // 태그등록
            for(String tag:tags){
                HashTagDto hashTag=hashTagMapper.findByTag(tag);
                if(hashTag==null) modify+=hashTagMapper.insertOne(tag);
                TripHashTagDto tripHashTag=new TripHashTagDto();
                tripHashTag.setTId(trip.getTId());
                tripHashTag.setTag(tag);
                modify+=tripHashTagMapper.insertOne(tripHashTag);
            }
        }
        if(delTags!=null){ // 태그삭제
            for(String tag:delTags){
                TripHashTagDto tripHashTag=new TripHashTagDto();
                tripHashTag.setTag(tag);
                tripHashTag.setTId(trip.getTId());
                modify+=tripHashTagMapper.deleteOne(tripHashTag);
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

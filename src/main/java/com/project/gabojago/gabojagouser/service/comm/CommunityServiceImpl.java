package com.project.gabojago.gabojagouser.service.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommImgDto;
import com.project.gabojago.gabojagouser.dto.comm.CommunityDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.mapper.comm.CommImgMapper;
import com.project.gabojago.gabojagouser.mapper.comm.CommunityMapper;
import com.project.gabojago.gabojagouser.mapper.user.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class CommunityServiceImpl implements CommunityService{
    private CommunityMapper communityMapper;
    private CommImgMapper commImgMapper;
    private UserMapper userMapper;
    //유저맵퍼..
    @Override
    public List<CommunityDto> list(UserDto loginUser) {
        if(loginUser!=null) userMapper.setLoginUserId(loginUser.getUId());
        List<CommunityDto> list=communityMapper.findAll();
        if(loginUser!=null)userMapper.setLoginUserIdNull();
        return list;
    }

    @Override
    public List<CommImgDto> imgList(int[] ciId) {
        List<CommImgDto> imgList=null;
        if(ciId!=null){
            imgList=new ArrayList<>();
            for(int iId: ciId){
                CommImgDto imgDto=commImgMapper.findByCiId(iId);
                imgList.add(imgDto);
            }
        }
        return imgList;
    }

    @Override
    public CommunityDto detail(int cId) {
        CommunityDto detail=communityMapper.findByCId(cId);
        return detail;
    }

    @Override
    @Transactional
    public int register(CommunityDto community) {
        int register=communityMapper.insertOne(community);
        if(community.getImgs()!=null){
            for(CommImgDto img:community.getImgs()){
                img.setCId(community.getCId());
                register+=commImgMapper.insertOne(img);
            }
        }
        return register;
    }

    @Override
    @Transactional
    public int modify(CommunityDto community, int[] delImgIds) {
        int modify=communityMapper.updateOne(community);
        if(community.getImgs()!=null){
            for(CommImgDto img:community.getImgs()){
                img.setCId(community.getCId());
                modify+=commImgMapper.insertOne(img);
            }
        }
        if(delImgIds!=null){
            for(int ciId:delImgIds){
                modify+=commImgMapper.deleteOne(ciId);
            }
        }
        return modify;
    }

    @Override
    public int remove(int cId) {
        int remove=communityMapper.deleteOne(cId);
        return remove;
    }
}

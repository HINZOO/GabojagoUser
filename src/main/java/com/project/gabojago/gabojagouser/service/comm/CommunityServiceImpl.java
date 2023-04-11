package com.project.gabojago.gabojagouser.service.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommunityDto;
import com.project.gabojago.gabojagouser.mapper.comm.CommunityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class CommunityServiceImpl  implements CommunityService{
    private CommunityMapper communityMapper;
    //유저맵퍼..
    @Override
    public List<CommunityDto> list() {
        List<CommunityDto> list=communityMapper.findAll();
        return list;
    }

    @Override
    public CommunityDto detail(int cId) {
        CommunityDto detail=communityMapper.findByCId(cId);
        return detail;
    }

    @Override
    public int register(CommunityDto community) {
        int register=communityMapper.insertOne(community);
        return register;
    }

    @Override
    public int modify(CommunityDto community) {
        int modify=communityMapper.updateOne(community);
        return modify;
    }

    @Override
    public int remove(int cId) {
        int remove=communityMapper.deleteOne(cId);
        return remove;
    }
}

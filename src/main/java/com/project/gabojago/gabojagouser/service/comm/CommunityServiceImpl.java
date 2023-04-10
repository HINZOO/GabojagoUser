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
        return null;
    }

    @Override
    public CommunityDto detail(int cId) {
        return null;
    }

    @Override
    public int register(CommunityDto community) {
        return 0;
    }

    @Override
    public int modify(CommunityDto community) {
        return 0;
    }

    @Override
    public int remove(int cId) {
        return 0;
    }
}

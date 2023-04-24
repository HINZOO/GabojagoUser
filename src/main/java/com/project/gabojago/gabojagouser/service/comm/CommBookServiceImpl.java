package com.project.gabojago.gabojagouser.service.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommBookmarkDto;
import com.project.gabojago.gabojagouser.mapper.comm.CommBookMarkMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommBookServiceImpl implements CommBookMarkService {
    private CommBookMarkMapper commBookMarkMapper;

    @Override
    public List<CommBookmarkDto> list(String uId) {
        List<CommBookmarkDto> list=commBookMarkMapper.findByUId(uId);
        return list;
    }

    @Override
    public int register(CommBookmarkDto commBookmarkDto) {
        int register=commBookMarkMapper.insertOne(commBookmarkDto);
        return register;
    }

    @Override
    public int remove(int cbookId) {
        int remove=commBookMarkMapper.deleteOne(cbookId);
        return remove;
    }
}

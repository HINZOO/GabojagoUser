package com.project.gabojago.gabojagouser.service.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommReplyDto;
import com.project.gabojago.gabojagouser.mapper.comm.CommReplyMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommReplyServiceImpl implements CommReplyService {
    private CommReplyMapper commReplyMapper;

    @Override
    public List<CommReplyDto> list(int cId) {
        List<CommReplyDto> list=commReplyMapper.findByCIdAndParentCrIdIsNull(cId);
        return list;
    }

    @Override
    public CommReplyDto detail(int ccId) {
        CommReplyDto detail=commReplyMapper.findByCcId(ccId);
        return detail;
    }

    @Override
    public int register(CommReplyDto commReply) {
        int register= commReplyMapper.insertOne(commReply);
        return register;
    }

    @Override
    public int modify(CommReplyDto commReply){
        int modify=commReplyMapper.updateOne(commReply);
        return modify;
    }

    @Override
    public int remove(int ccId) {
        int remove=commReplyMapper.deleteOne(ccId);
        return remove;
    }
}

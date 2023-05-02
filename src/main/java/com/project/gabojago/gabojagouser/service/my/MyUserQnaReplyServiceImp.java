package com.project.gabojago.gabojagouser.service.my;

import com.project.gabojago.gabojagouser.dto.my.MyUserQnaReplyDto;
import com.project.gabojago.gabojagouser.mapper.my.MyUserQnaReplyMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MyUserQnaReplyServiceImp implements MyUserQnaReplyService{
    private MyUserQnaReplyMapper myUserQnaReplyMapper;

    @Override
    public List<MyUserQnaReplyDto> list(int qId) {
        List<MyUserQnaReplyDto> list=myUserQnaReplyMapper.findByQIdAndParentQrIdIsNull(qId);
        return list;
    }

    @Override
    public boolean replyStatus(int qrId) {
        return false;
    }
}

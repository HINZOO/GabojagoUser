package com.project.gabojago.gabojagouser.service.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommReplyDto;

import java.util.List;

public interface CommReplyService {
    //등록 //수정 //삭제 // 상세 //(게시판번호)리스트

    List<CommReplyDto> list(int cId);
    CommReplyDto detail(int ccId);
    int register(CommReplyDto commComment);
    int modify(CommReplyDto comComment);
    int remove(int ccId);
}

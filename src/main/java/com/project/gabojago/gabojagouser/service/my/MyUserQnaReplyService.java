package com.project.gabojago.gabojagouser.service.my;

import com.project.gabojago.gabojagouser.dto.my.MyUserQnaReplyDto;

import java.util.List;

public interface MyUserQnaReplyService {
    List<MyUserQnaReplyDto> list(int qrId);
    boolean replyStatus(int qrId);

}

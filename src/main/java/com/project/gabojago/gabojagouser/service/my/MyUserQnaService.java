package com.project.gabojago.gabojagouser.service.my;

import com.project.gabojago.gabojagouser.dto.my.MyUserQnaDto;
import com.project.gabojago.gabojagouser.dto.my.MyUserQnaReplyDto;
import com.project.gabojago.gabojagouser.dto.my.QnaPageDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;

import java.util.List;

public interface MyUserQnaService {
    List<MyUserQnaDto> list();
    List<MyUserQnaDto> list(String uId,QnaPageDto pageDto);
    MyUserQnaDto detail(int qId,UserDto loginUser);
    int register(MyUserQnaDto myUserQna);
    int modify(MyUserQnaDto myUserQna);
    int remove(int qId);
}

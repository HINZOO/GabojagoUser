package com.project.gabojago.gabojagouser.service.my;

import com.project.gabojago.gabojagouser.dto.my.MyUserQnaDto;

import java.util.List;

public interface MyUserQnaService {
    List<MyUserQnaDto> list();
    MyUserQnaDto detail(int qId);
    int register(MyUserQnaDto myUserQna);
    int modify(MyUserQnaDto myUserQna);
    int remove(int qId);
}

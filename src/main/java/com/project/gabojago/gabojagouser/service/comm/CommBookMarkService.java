package com.project.gabojago.gabojagouser.service.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommBookmarkDto;

import java.util.List;

public interface CommBookMarkService {
    List<CommBookmarkDto> list(String uId);
    int register(CommBookmarkDto commBookmarkDto);
    int remove(int cbookId);

}

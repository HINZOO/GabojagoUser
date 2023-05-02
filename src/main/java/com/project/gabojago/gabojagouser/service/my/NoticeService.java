package com.project.gabojago.gabojagouser.service.my;

import com.project.gabojago.gabojagouser.dto.my.NoticeDto;
import com.project.gabojago.gabojagouser.dto.my.NoticePageDto;

import java.util.List;

public interface NoticeService {
    List<NoticeDto> list(NoticePageDto pageDto);
    NoticeDto detail(int nId);
}

package com.project.gabojago.gabojagouser.service.my;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.gabojago.gabojagouser.dto.my.NoticeDto;
import com.project.gabojago.gabojagouser.dto.my.NoticePageDto;
import com.project.gabojago.gabojagouser.mapper.my.NoticeMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoticeServiceImpl implements NoticeService{
    private NoticeMapper noticeMapper;

    @Override
    public List<NoticeDto> list(NoticePageDto pageDto) {
        List<NoticeDto> list=noticeMapper.findAll(pageDto);
        PageHelper.startPage(pageDto.getPageNum(),pageDto.getPageSize(),pageDto.getOrderBy());
        return list;
    }

    @Override
    public NoticeDto detail(int nId) {
        NoticeDto detail=noticeMapper.findByNId(nId);
        noticeMapper.updateViewCountByNId(nId);
        return detail;
    }
}

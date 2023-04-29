package com.project.gabojago.gabojagouser.service.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellBookmarksDto;
import com.project.gabojago.gabojagouser.dto.sells.SellCartDto;
import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import com.project.gabojago.gabojagouser.mapper.sells.SellBookMarksMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SellBookMarksServiceImp implements SellBookMarksService{
    private SellBookMarksMapper sellBookMarksMapper;
    @Override
    public List<SellBookmarksDto> List(String uId) {
        List<SellBookmarksDto> list = sellBookMarksMapper.findByUId(uId);
        return list;
    }

    @Override
    public List<SellsDto> bookmarkedSellList(String uId) {
        List<SellsDto> list = sellBookMarksMapper.findSellsByUId(uId);
        return list;
    }

    @Override
    public int register(SellBookmarksDto sellBookmarksDto) {
        int register = sellBookMarksMapper.insertOne(sellBookmarksDto);
        return register;
    }

    @Override
    public int remove(int sbId) {
        int remove = sellBookMarksMapper.deleteOne(sbId);
        return remove;
    }
}

package com.project.gabojago.gabojagouser.service.sells;

import com.github.pagehelper.PageHelper;
import com.project.gabojago.gabojagouser.dto.sells.SellImgsDto;
import com.project.gabojago.gabojagouser.dto.sells.SellPageDto;
import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import com.project.gabojago.gabojagouser.dto.sells.SellsOptionDto;
import com.project.gabojago.gabojagouser.mapper.sells.SellImgsMapper;
import com.project.gabojago.gabojagouser.mapper.sells.SellsMapper;
import com.project.gabojago.gabojagouser.mapper.sells.SellsOptionMapper;
import com.project.gabojago.gabojagouser.mapper.user.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SellsServiceImp implements SellsService{
    private SellsMapper sellsMapper;
    private SellsOptionMapper sellsOptionMapper;
    private SellImgsMapper sellImgsMapper;
    private UserMapper userMapper;
    @Override
    public List<SellsDto> List(SellPageDto pageDto) {
        PageHelper.startPage(pageDto.getPageNum(),pageDto.getPageSize(),pageDto.getOrder());
        List<SellsDto> sells=sellsMapper.findAll(pageDto);;
        return sells;
    }

    @Override
    public List<SellsDto> findByTitle(String title) {

        List<SellsDto> TitleList=sellsMapper.findByTitle(title);
        return TitleList;
    }

    @Override
    public List<SellsDto> findByCategory(String category, SellPageDto pageDto) {
        PageHelper.startPage(pageDto.getPageNum(),pageDto.getPageSize(),pageDto.getOrder());
        List<SellsDto> categoryList=sellsMapper.findByCategory(category,pageDto);
        return categoryList;
    }

    @Override
    public List<SellImgsDto> imgList(int[] simgId) {
        List<SellImgsDto> imgList=null;
        if(simgId!=null){
            imgList=new ArrayList<>();
            for (int id : simgId){
                SellImgsDto imgsDto=sellImgsMapper.findBySimgId(id);
                imgList.add(imgsDto);
            }
        }
        return imgList;
    }

    @Override
    public SellsDto detail(int sId) {
        return this.sellsMapper.findBySId(sId);
    }

    @Override
    public int optionRegister(SellsOptionDto sellsOption) {
        return this.sellsOptionMapper.insertOne(sellsOption);
    }

    @Override
    public int imgRegister(SellImgsDto sellImgsDto) {
        return this.sellImgsMapper.insertOne(sellImgsDto);
    }

    @Override
    public int register(SellsDto sells) {
        return this.sellsMapper.insertOne(sells);
    }

    @Override
    public int modify(SellsDto sells, int[] delImgIds, int[] delOptionIds) {
        int modify=sellsMapper.updateOne(sells);
        if (delImgIds!=null){
            for (int simgId: delImgIds){
                modify+=sellImgsMapper.deleteOne(simgId);
            }
        }
        if (delOptionIds!=null){
            for (int oId: delOptionIds ){
                modify+=sellsOptionMapper.deleteOne(oId);
            }
        }

        return modify;
    }

    @Override
    public int remove(int sId) {
        return this.sellsMapper.deleteOne(sId);
    }
}

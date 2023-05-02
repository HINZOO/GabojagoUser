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
    public List<SellsDto> list(String uId, SellPageDto pageDto) {
        PageHelper.startPage(pageDto.getPageNum(),pageDto.getPageSize(),pageDto.getOrder());
        List<SellsDto> list=sellsMapper.findByUId(uId);
        return list;
    }

    @Override
    public List<SellImgsDto> imgList(List<Integer> simgId) {
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
        int register=0;
        System.out.println("sells11111 = " + sells);
        register = sellsMapper.insertOne(sells);
        System.out.println("sells1 = " + sells);
        if (sells.getSellImgs() != null) {
            for (SellImgsDto img : sells.getSellImgs()){
                img.setSId(sells.getSId());
                register += sellImgsMapper.insertOne(img);
            }
        }
        if (sells.getSellOption()!=null){
            for (SellsOptionDto optin : sells.getSellOption()){
                optin.setSId(sells.getSId());
                register+= sellsOptionMapper.insertOne(optin);
            }
        }

        return register;
    }

    @Override
    public int modify(SellsDto sells,List<Integer> delImgIds, int[] delOptionIds) {
        int modify=sellsMapper.updateOne(sells);
        System.out.println("sells+delImgIds+delOptionIds = " + sells+delImgIds+delOptionIds);
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
        if (sells.getSellImgs() != null) {
            for (SellImgsDto img : sells.getSellImgs()){
                System.out.println("img다 = " + img+sells.getSId());
                img.setSId(sells.getSId());
                modify += sellImgsMapper.insertOne(img);
            }
        }
        if (sells.getSellOption()!=null){
            for (SellsOptionDto option : sells.getSellOption()){
                option.setSId(sells.getSId());
                modify+= sellsOptionMapper.insertOne(option);
            }
        }

        return modify;
    }

    @Override
    public int remove(int sId) {
        return this.sellsMapper.deleteOne(sId);
    }

    @Override
    public SellsDto findBySId(int sId) {
        SellsDto sid = sellsMapper.findBySId(sId);
        return sid;
    }
}

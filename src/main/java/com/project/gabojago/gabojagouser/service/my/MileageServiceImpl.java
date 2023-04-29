package com.project.gabojago.gabojagouser.service.my;

import com.project.gabojago.gabojagouser.dto.my.MileageDto;
import com.project.gabojago.gabojagouser.mapper.my.MileageMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class MileageServiceImpl implements MileageService{
    private MileageMapper mileageMapper;
    @Override
    public List<MileageDto> list(String uId) {
        List<MileageDto> list=mileageMapper.findByUId(uId);
        return list;
    }

    @Override
    public Integer sumMileage(String uId) {
        Integer sumMileage=mileageMapper.sumByUId(uId);
        return sumMileage!=null?sumMileage:0;
    }

    @Override
    public int register(MileageDto mileageDto) {
        int register=mileageMapper.insertOne(mileageDto);
        return register;
    }

    @Override
    public int modify(MileageDto mileageDto) {
        int modify = mileageMapper.updateOne(mileageDto);
        return modify;
    }
}

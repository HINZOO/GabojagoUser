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
    public int sumMileage(String uId) {
        int sumMileage=mileageMapper.sumByUId(uId);
        return sumMileage;
    }

    @Override
    public int register(MileageDto mileageDto) {
        int register=mileageMapper.insertOne(mileageDto);
        return register;
    }
}

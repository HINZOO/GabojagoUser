package com.project.gabojago.gabojagouser.service.my;

import com.project.gabojago.gabojagouser.dto.my.MileageDto;

import java.util.List;

public interface MileageService {
    List<MileageDto> list(String uId);
    Integer sumMileage(String uId);
    int register(MileageDto mileageDto);

    int modify(MileageDto mileageDto);

}

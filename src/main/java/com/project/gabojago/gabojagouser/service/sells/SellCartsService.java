package com.project.gabojago.gabojagouser.service.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellCartDto;

import java.util.List;

public interface SellCartsService {
    List<SellCartDto> List(String uId);

    int register(SellCartDto sellCartDto);
    int remove(int cartId);

}

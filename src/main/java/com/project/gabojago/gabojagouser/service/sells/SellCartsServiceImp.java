package com.project.gabojago.gabojagouser.service.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellCartDto;
import com.project.gabojago.gabojagouser.mapper.sells.SellCartsMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class SellCartsServiceImp implements SellCartsService{
    private SellCartsMapper sellCartsMapper;
    @Override
    public List<SellCartDto> List(String uId) {
        List<SellCartDto> cartlist = sellCartsMapper.findByUId(uId);
        return cartlist;
    }

    @Override
    public int register(SellCartDto sellCartDto) {
        int register=sellCartsMapper.insertOne(sellCartDto);
        return register;
    }

    @Override
    public int remove(int cartId) {
        int remove = sellCartsMapper.deleteOne(cartId);
        return remove;
    }
}
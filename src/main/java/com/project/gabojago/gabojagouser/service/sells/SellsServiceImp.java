package com.project.gabojago.gabojagouser.service.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import com.project.gabojago.gabojagouser.mapper.sells.SellsMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SellsServiceImp implements SellsService{
    private SellsMapper sellsMapper;
    @Override
    public List<SellsDto> List() {
        return this.sellsMapper.findAll();
    }

    @Override
    public List<SellsDto> findByCategory(String category) {
        return this.sellsMapper.findByCategory(category);
    }

    @Override
    public SellsDto detail(int sId) {
        return this.sellsMapper.findBySId(sId);
    }

    @Override
    public int register(SellsDto sells) {
        return this.sellsMapper.insertOne(sells);
    }

    @Override
    public int modify(SellsDto sells) {
        return this.sellsMapper.updateOne(sells);
    }

    @Override
    public int remove(int sId) {
        return this.sellsMapper.deleteOne(sId);
    }
}

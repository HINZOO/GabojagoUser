package com.project.gabojago.gabojagouser.controller;

import com.project.gabojago.gabojagouser.dto.comm.CommPageDto;
import com.project.gabojago.gabojagouser.dto.comm.CommunityDto;
import com.project.gabojago.gabojagouser.dto.sells.SellPageDto;
import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import com.project.gabojago.gabojagouser.dto.trip.TripDto;
import com.project.gabojago.gabojagouser.mapper.trip.TripMapper;
import com.project.gabojago.gabojagouser.service.comm.CommunityService;
import com.project.gabojago.gabojagouser.service.sells.SellsService;
import com.project.gabojago.gabojagouser.service.trip.TripService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {
    private TripService tripService;
    private CommunityService commService;
    private SellsService sellsService;

    @GetMapping
    public String indexPage(Model model){
        CommPageDto commPageDto=new CommPageDto();
        SellPageDto sellPageDto=new SellPageDto();
        List<TripDto> tripList=tripService.list();
        List<CommunityDto> commList=commService.list(null,commPageDto);
        List<SellsDto> sellsList=sellsService.List(sellPageDto);
        model.addAttribute("trip",tripList);
        model.addAttribute("comm",commList);
        model.addAttribute("sells",sellsList);
        return "/index";
    }
}

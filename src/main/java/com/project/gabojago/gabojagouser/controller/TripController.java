package com.project.gabojago.gabojagouser.controller;

import com.project.gabojago.gabojagouser.dto.trips.TripDto;
import com.project.gabojago.gabojagouser.service.TripService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/trip")
@Log4j2
public class TripController {
    private TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/list.do")
    public String list(Model model){
        List<TripDto> trips;
        trips=tripService.list();
        model.addAttribute("trips",trips);
        return "/trip/list";
    }


}

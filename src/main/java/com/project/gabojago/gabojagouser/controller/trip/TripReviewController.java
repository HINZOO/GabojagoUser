package com.project.gabojago.gabojagouser.controller.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripReviewDto;
import com.project.gabojago.gabojagouser.service.trip.TripReviewService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/review")
@Log4j2
public class TripReviewController {
    private TripReviewService tripReviewService;

    public TripReviewController(TripReviewService tripReviewService) {
        this.tripReviewService = tripReviewService;
    }

//    @GetMapping("/{tId}/detail.do")
//    public @ResponseBody TripReviewDto detail(){
//
//    }

    class HandlerDto {
        private int register;
        private int modify;
        private int remove;
    }

    @PostMapping("/handler.do")
    public HandlerDto registerHandler(
            @ModelAttribute TripReviewDto review
    ){
        log.info(review);

        HandlerDto handlerDto=new HandlerDto();
        return handlerDto;
    }





}

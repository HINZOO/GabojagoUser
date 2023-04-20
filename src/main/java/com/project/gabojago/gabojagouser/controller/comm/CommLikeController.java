package com.project.gabojago.gabojagouser.controller.comm;

import com.project.gabojago.gabojagouser.service.comm.CommLikeService;
import groovy.util.logging.Log4j2;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comm/like")
@AllArgsConstructor
@Log4j2
public class CommLikeController {
    private CommLikeService commLikeService;

    @GetMapping("/{cId}/read.do")
    public String readLikeStatusCnt(

    ){
        return "";
    }
}

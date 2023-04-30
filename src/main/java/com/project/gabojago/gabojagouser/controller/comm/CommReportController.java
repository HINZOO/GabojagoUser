package com.project.gabojago.gabojagouser.controller.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommReportDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.comm.CommReportService;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/comm/report")
@Log4j2
public class CommReportController {
    CommReportService commReportService;

    public CommReportController(CommReportService commReportService) {
        this.commReportService = commReportService;
    }
    @Data
    class HandlerDto{
        private int handler;
    }
    @PostMapping("/handler.do")
    public @ResponseBody HandlerDto reportRegisterHandler(
            @SessionAttribute UserDto loginUser,
            @ModelAttribute CommReportDto commReportDto,
            RedirectAttributes redirectAttributes
            ){
        log.info(commReportDto);
        HandlerDto handlerDto=new HandlerDto();
        String msg="";

        int register=commReportService.register(commReportDto);
        handlerDto.setHandler(register);
        return handlerDto;

    }

}

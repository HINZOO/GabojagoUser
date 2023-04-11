package com.project.gabojago.gabojagouser.controller.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommunityDto;
import com.project.gabojago.gabojagouser.service.comm.CommunityService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/comm")
@AllArgsConstructor
@Log4j2
public class CommController {
    private CommunityService communityService;
    @GetMapping("/list.do")
    public String list(Model model){
        List<CommunityDto> communities;
        communities=communityService.list();
        model.addAttribute("communities",communities);
        return "/comm/list";
    }

    @GetMapping("/{cId}/detail.do")
    public String detail(Model model,
                         @PathVariable int cId){
        CommunityDto comm=communityService.detail(cId);
        model.addAttribute("c",comm);
        return "/comm/detail";
    }
}

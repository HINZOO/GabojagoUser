package com.project.gabojago.gabojagouser.controller;

import com.github.pagehelper.PageInfo;
import com.project.gabojago.gabojagouser.dto.my.NoticeDto;
import com.project.gabojago.gabojagouser.dto.my.NoticePageDto;
import com.project.gabojago.gabojagouser.dto.trip.TripDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.my.NoticeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    //기타 Dto,Mapper,service는  my폴더에 저장.

    private NoticeService noticeService;

    @GetMapping("/list.do")
    public String noticePage(
            @SessionAttribute(required = false) UserDto loginUser,
            Model model
    ){
        NoticePageDto noticePageDto = new NoticePageDto();
        List<NoticeDto> notices=noticeService.list(noticePageDto);
        PageInfo<NoticeDto> PageInfo=new PageInfo<>(notices);
        model.addAttribute("page",PageInfo);
        model.addAttribute("notices",notices);
           return "/my/notice";
    }
    @GetMapping("/{nId}/detail.do")
    public String noticeDetail(
            @PathVariable int nId,
            @SessionAttribute(required = false) UserDto loginUser,
            Model model
    ){
        NoticeDto noticeDto = new NoticeDto();
        noticeDto=noticeService.detail(nId);
        model.addAttribute("n",noticeDto);
        return "/my/noticeDetail";
    }

}

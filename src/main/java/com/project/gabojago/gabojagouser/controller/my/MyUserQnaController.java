package com.project.gabojago.gabojagouser.controller.my;

import com.fasterxml.jackson.core.io.IOContext;
import com.project.gabojago.gabojagouser.dto.comm.CommImgDto;
import com.project.gabojago.gabojagouser.dto.comm.CommunityDto;
import com.project.gabojago.gabojagouser.dto.my.MyUserQnaDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.my.MyUserQnaService;
import lombok.AllArgsConstructor;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/my/qna")
@Log4j2
public class MyUserQnaController {
    private MyUserQnaService myUserQnaService;

    @Value("${static.path}")
    private String staticPath;
    public MyUserQnaController(MyUserQnaService myUserQnaService) {
        this.myUserQnaService = myUserQnaService;
    }
    @GetMapping("/{uId}/serviceList.do")
    private String serviceList(Model model,
                               @ModelAttribute MyUserQnaDto myUserQna,
                               @SessionAttribute UserDto loginUser,
                               @PathVariable String uId
                              ){

        List<MyUserQnaDto> list= myUserQnaService.list();
        model.addAttribute("qnaList",list);

        return "/my/qna/serviceList";
    }
    @GetMapping ("/service.do")
    private String serviceForm(Model model,
                               @SessionAttribute(required = false) UserDto loginUser,
                               RedirectAttributes redirectAttributes) {
        if (loginUser == null) {
            String msg = "로그인한 사용자만 이용할 수 있습니다.";
            redirectAttributes.addFlashAttribute("msg", msg);
            return "redirect:/user/login.do";
        }
        return "/my/qna/service";
    }
    @PostMapping("/service.do")
    public String serviceAction(
            @SessionAttribute UserDto loginUser,
            @ModelAttribute MyUserQnaDto board,
            @RequestParam(name = "img",required = false) MultipartFile img) throws IOException {

        String redirectPage="redirect:/my/qna/service.do";
        if(!loginUser.getUId().equals(board.getUId())) return redirectPage;
        MyUserQnaDto imgDto=null;
        if(img!=null){
                if(!img.isEmpty()){
                    String[] contentTypes=img.getContentType().split("/");
                    if(contentTypes[0].equals("image")){
                        String fileName=System.currentTimeMillis()+"_"+(int)(Math.random()*10000)+"."+contentTypes[1];
                        Path path= Paths.get(staticPath + "/public/img/my/" + fileName);
                        img.transferTo(path);
                        imgDto.setFilePath("/public/img/my/" + fileName);
                    }
                }
            }
        int register=0;
        try {
            register=myUserQnaService.register(board);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        if (register>0){
            redirectPage="redirect:/my/qna/serviceList.do";
        }else {
                File imgFile = new File(staticPath + imgDto.getFilePath());
                if (imgFile.exists()) imgFile.delete();
        }
        return redirectPage;
    }
    @GetMapping("/{qId}/detail.do")
    private String detail(
            Model model,
            @PathVariable int qId,
            @SessionAttribute(required = false) UserDto loginUser){
        MyUserQnaDto qna=myUserQnaService.detail(qId, loginUser);
        model.addAttribute("q",qna);
        return "/my/qna/detail";
    }


    @GetMapping("/{qId}/modify.do")
    public String modifyForm(Model model,
                             @PathVariable int qId,
                             @SessionAttribute UserDto loginUser,
                             RedirectAttributes redirectAttributes){
        if(loginUser==null){
            String msg="로그인한 사용자만 이용할 수 있습니다.";
            redirectAttributes.addFlashAttribute("msg",msg);
            return "redirect:/user/login.do";
        }
        MyUserQnaDto qna= myUserQnaService.detail(qId,loginUser);
        model.addAttribute("q",qna);
        return "/my/qna/modify";
    }
    @PostMapping("/modify.do")
    public String modifyAction(

            @ModelAttribute MyUserQnaDto board,
            @RequestParam(value="delImgId",required = false) int delImgId,
            @RequestParam(name = "img",required = false) MultipartFile img
    ) throws IOException {

        String redirectPage="redirect:/my/qna/"+board.getQId()+"/modify.do";

        int modify=0;
        MyUserQnaDto imgDto=null;
        if(img!=null){
            if(!img.isEmpty()){
                String[] contentTypes=img.getContentType().split("/");
                if(contentTypes[0].equals("image")){
                    String fileName=System.currentTimeMillis()+"_"+(int)(Math.random()*10000)+"."+contentTypes[1];
                    Path path= Paths.get(staticPath + "/public/img/my/" + fileName);
                    img.transferTo(path);
                    imgDto.setFilePath("/public/img/my/" + fileName);
                }
            }
        }
        try {
            modify=myUserQnaService.modify(board);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return redirectPage;
    }

    @GetMapping("/{qId}/remove.do")
    public String removeAction(@PathVariable int qId,
                               @SessionAttribute UserDto loginUser,
                               RedirectAttributes redirectAttributes){
        String redirectPage="redirect:/my/qna/"+loginUser.getUId()+"/serviceList.do";
        MyUserQnaDto board=null;

        int del=myUserQnaService.remove(qId);
        String msg="게시글을 삭제하였습니다.";
        redirectAttributes.addFlashAttribute("msg",msg);
        return redirectPage;
    }

}

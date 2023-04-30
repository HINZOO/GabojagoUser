package com.project.gabojago.gabojagouser.controller.comm;

import com.github.pagehelper.PageInfo;
import com.project.gabojago.gabojagouser.controller.my.BookMarkController;
import com.project.gabojago.gabojagouser.dto.comm.CommBookmarkDto;
import com.project.gabojago.gabojagouser.dto.comm.CommImgDto;
import com.project.gabojago.gabojagouser.dto.comm.CommPageDto;
import com.project.gabojago.gabojagouser.dto.comm.CommunityDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.comm.CommunityService;
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
@RequestMapping("/comm")
@Log4j2
public class CommController {
    private CommunityService communityService;

    @Value("${static.path}")
    private String staticPath;
    public CommController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/list.do")
    public String list(Model model,
                       @SessionAttribute(required = false) UserDto loginUser,
                       CommPageDto pageDto){
        List<CommunityDto> communities;
        communities=communityService.list(loginUser,pageDto);
        PageInfo<CommunityDto> pageComms=new PageInfo<>(communities);
        model.addAttribute("page",pageComms);
        model.addAttribute("communities",communities);
        return "/comm/list";
    }

    @GetMapping("/{cId}/detail.do")
    public String detail(Model model,
                         @PathVariable int cId,
                         @SessionAttribute(required = false) UserDto loginUser){
        CommunityDto comm=communityService.detail(cId,loginUser);
        model.addAttribute("c",comm);
        return "/comm/detail";
    }
    @GetMapping("/register.do")
    public String registerForm(
            @SessionAttribute(required = false) UserDto loginUser,
            RedirectAttributes redirectAttributes){
        if(loginUser==null){
            String msg="로그인한 사용자만 이용할 수 있습니다.";
            redirectAttributes.addFlashAttribute("msg",msg);
            return "redirect:/user/login.do";
        }
        if(loginUser.getPlans()==null){
            String msg="일정을 만들어야지만 글을 쓸 수 있습니다!";
            redirectAttributes.addFlashAttribute("msg",msg);
            return "redirect:/plan/list.do";
        }
        if(loginUser.getPermission().equals("PARTNER")){
            String msg="USER만 글을 쓸 수 있습니다.";
            redirectAttributes.addFlashAttribute("msg",msg);
            return "redirect:/comm/list.do";
        }
        return "/comm/register";
    }
    @PostMapping("/register.do")
    public String registerAction(
            @SessionAttribute(required = false) UserDto loginUser,
            @ModelAttribute CommunityDto commBoard,
            @RequestParam(value ="img",required = false) MultipartFile[] imgs
    ) throws IOException {
        String redirectPage = "redirect:/comm/register.do";
        //System.out.println("commBoard = " + commBoard);
        //if(!loginUser.getUId().equals(commBoard.getCId())) return redirectPage;
        List<CommImgDto> commImgs = null;
        if (imgs != null) {
            commImgs = new ArrayList<>();
            for (MultipartFile img : imgs) {
                if (!img.isEmpty()) {
                    String[] contentTypes = img.getContentType().split("/");
                    if (contentTypes[0].equals("image")) {
                        String fileName = System.currentTimeMillis() + "_" + (int) (Math.random() * 10000) + "." + contentTypes[1];
                        Path path = Paths.get(staticPath + "/public/img/comm/" + fileName);//컴퓨터의 실제 저장위치.
                        img.transferTo(path);
                        CommImgDto imgDto = new CommImgDto();
                        imgDto.setImgPath("/public/img/comm/" + fileName);//서버배포경로
                        commImgs.add(imgDto);
                    }
                }
            }
        }else{
            CommImgDto basicImg=new CommImgDto();
            basicImg.setCId(commBoard.getCId());
            basicImg.setImgMain(false);
            basicImg.setImgPath("/public/img/comm/basic1.jpg");
            commImgs = new ArrayList<>();
            boolean add = commImgs.add(basicImg);
        }
        commBoard.setImgs(commImgs);
        int register = 0;
        try {
            register = communityService.register(commBoard);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (register > 0) {
            redirectPage = "redirect:/comm/list.do";
        } else {
            if (commImgs != null) {
                for (CommImgDto i : commImgs) {
                    File imgFile = new File(staticPath + i.getImgPath());
                    if (imgFile.exists()) imgFile.delete();
                }
            }
        }

        return redirectPage;
    }

    @GetMapping("/{cId}/modify.do")
    public String modifyForm(Model model,
                             @PathVariable int cId,
                             @SessionAttribute UserDto loginUser,
                             RedirectAttributes redirectAttributes){
        if(loginUser==null){
            String msg="로그인한 사용자만 이용할 수 있습니다.";
            redirectAttributes.addFlashAttribute("msg",msg);
            return "redirect:/user/login.do";
        }
        CommunityDto comm= communityService.detail(cId, loginUser);
        model.addAttribute("c",comm);
        return "/comm/modify";
    }

    @PostMapping("/modify.do")
    public String modifyAction(
            @ModelAttribute CommunityDto commBoard,
            @RequestParam(value="delImgId",required = false) int[] delImgIds,
            @RequestParam(value="img",required = false) MultipartFile[] imgs
    ) throws IOException {

        String redirectPage="redirect:/comm/"+commBoard.getCId()+"/modify.do";
        List<CommImgDto> imgDtos=null;
        int modify=0;
        if(imgs!=null){
            imgDtos=new ArrayList<>();

            for(MultipartFile img:imgs){
                if(!img.isEmpty()){
                    String[] contentTypes=img.getContentType().split("/");
                    if(contentTypes[0].equals("image")){
                        String fileName=System.currentTimeMillis()+"_"+(int)(Math.random()*10000)+"."+contentTypes[1];
                        Path path = Paths.get(staticPath + "/public/img/comm/" + fileName);
                        img.transferTo(path);
                        CommImgDto imgDto=new CommImgDto();
                        imgDto.setCId(commBoard.getCId());
                        imgDto.setImgPath("/public/img/comm/"+fileName);//서버배포경로
                        imgDtos.add(imgDto);
                    }
                }
            }
        }
        commBoard.setImgs(imgDtos);
        try{
            if(delImgIds!=null) imgDtos=communityService.imgList(delImgIds);
            modify=communityService.modify(commBoard,delImgIds);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        if(modify>0){
            if(imgDtos!=null){
                for(CommImgDto i:imgDtos){
                    File imgFile=new File(staticPath+i.getImgPath());
                    if(imgFile.exists()) imgFile.delete();
                }
            }
            redirectPage="redirect:/comm/list.do";
        }

        return redirectPage;

    }

    @GetMapping("/{cId}/remove.do")
    public String removeAction(@PathVariable int cId,
                               @SessionAttribute UserDto loginUser,
                               RedirectAttributes redirectAttributes){
        String redirectPage="redirect:/comm/list.do";
        CommunityDto board=null;

        int del=communityService.remove(cId);
        String msg="게시글을 삭제하였습니다.";
        redirectAttributes.addFlashAttribute("msg",msg);
        return redirectPage;
    }


}
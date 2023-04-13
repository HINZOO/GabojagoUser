package com.project.gabojago.gabojagouser.controller.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommImgDto;
import com.project.gabojago.gabojagouser.dto.comm.CommunityDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.comm.CommunityService;
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
    @GetMapping("/register.do")
    public String registerForm(
            @SessionAttribute(required = false) UserDto loginUser,
            RedirectAttributes redirectAttributes){
        if(loginUser==null){
            String msg="로그인한 사용자만 이용할 수 있습니다.";
            redirectAttributes.addFlashAttribute("msg",msg);
            return "redirect:/comm/list.do";
        }
        return "/comm/register";
    }
    @PostMapping("/register.do")
    public String registerAction(
            @SessionAttribute(required = false) UserDto loginUser,
            @ModelAttribute CommunityDto commBoard,
            @RequestParam("files") MultipartFile[] imgs
            ) throws IOException {
        String redirectPage="redirect:/comm/register.do";
        //if(!loginUser.getUId().equals(commBoard.getCId())) return redirectPage;
        List<CommImgDto> commImgs=null;
        if(imgs!=null){
            commImgs=new ArrayList<>();
            for(MultipartFile img:imgs){
                if(!img.isEmpty()){
                    String[] contentTypes=img.getContentType().split("/");
                    if(contentTypes[0].equals("image")){
                        String fileName=System.currentTimeMillis()+"_"+(int)(Math.random()*10000)+"."+contentTypes[1];
                        Path path = Paths.get(staticPath+"/public/img/comm/"+fileName);//컴퓨터의 실제 저장위치.
                        img.transferTo(path);
                        CommImgDto imgDto=new CommImgDto();
                        imgDto.setImgPath("/public/img/comm/"+fileName);//서버배포경로
                        commImgs.add(imgDto);
                    }
                }
            }
        }
        commBoard.setImgs(commImgs);
        log.info(commBoard);
        int register=0;
        try{
            register=communityService.register(commBoard);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        if(register>0){
            redirectPage="redirect:/comm/list.do";
        }else{
            if(commImgs!=null){
                for(CommImgDto i:commImgs){
                    File imgFile=new File(staticPath+i.getImgPath());
                    if(imgFile.exists()) imgFile.delete();
                }
            }
        }
        return redirectPage;
    }
}

package com.project.gabojago.gabojagouser.controller.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripDto;
import com.project.gabojago.gabojagouser.dto.trip.TripImgDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.trip.TripService;
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
@RequestMapping("/trip")
@Log4j2
public class TripController {
    private TripService tripService;

    @Value("${img.upload.path}")
    private String uploadPath;
    @Value("${static.path}")
    private String staticPath;


    public TripController(TripService tripService) {
        this.tripService = tripService;
    }


    @GetMapping("/{tId}/remove.do") // db 정보 삭제 + 이미지 실제 삭제
    public String removeAction(
            @PathVariable int tId,
//            @SessionAttribute UserDto loginUser
        RedirectAttributes redirectAttributes
    ){
        String redirectPage="redirect:/trip/list.do";
        String msg="삭제실패";
        TripDto trip=null;
        List<TripImgDto> imgDtos=null;
        int remove=0;
        try{
            trip=tripService.detail(tId);
            imgDtos=trip.getImgs();
            remove=tripService.remove(tId);
        }catch (Exception e){
            log.error(e);
        }

        if(remove>0){
            if(imgDtos!=null){
                for(TripImgDto ti : imgDtos){
                    File imgFile = new File(staticPath + ti.getImgPath());
                    System.out.println("ti.getImgPath() = " + ti.getImgPath());
                    if(imgFile.exists()) imgFile.delete();
                }
            }
            msg="삭제성공!";
        }
        redirectAttributes.addFlashAttribute("msg",msg);
        return redirectPage;
    }


    @GetMapping("/{tId}/modify.do")
    public String modifyForm(
            Model model,
            @PathVariable int tId
//            @SessionAttribute UserDto loginUser
    ) {
        TripDto trip = tripService.detail(tId);
        model.addAttribute("t", trip);
        return "/trip/modify";
    }

    @PostMapping("/modify.do")
    public String modifyAction(
            @ModelAttribute TripDto trip,
            @RequestParam(name="img", required = false) MultipartFile [] imgs,
            @RequestParam(value="delImgId", required = false) int[] delImgIds,
            RedirectAttributes redirectAttributes
    ) {
        String redirectPage = "redirect:/trip/" + trip.getTId() + "/modify.do";
        String msg="";
        // 제목 입력 여부 확인
        if (trip.getTitle() == null || trip.getTitle().equals("")) {
            msg = "제목을 입력하세요.";
            redirectAttributes.addFlashAttribute("msg",msg);
            return redirectPage;
        }

        List<TripImgDto> imgDtos = null;
        if(imgs!=null){
            imgDtos=new ArrayList<>();
            for(MultipartFile img : imgs){
                if(!img.isEmpty()){
                    String[] contentTypes=img.getContentType().split("/");
                    if(contentTypes[0].equals("image")){
                        String fileName=System.currentTimeMillis()+"_"+(int)(Math.random()*10000)+"."+contentTypes[1];
                        Path path=Paths.get(uploadPath+"/trip/"+fileName);
                        try {
                            img.transferTo(path);
                        } catch (IOException e) {
                            log.error(e.getMessage());
                        }
                        TripImgDto imgDto=new TripImgDto();
                        imgDto.setImgPath("/public/img/trip/"+fileName);
                        imgDtos.add(imgDto);
                    }
                }
            }
        }
        trip.setImgs(imgDtos);
        int modify = 0;
        msg="등록실패";
        try {
            if (delImgIds != null) imgDtos = tripService.imgList(delImgIds); // 삭제할 이미지아이디가 있으면 => 수정
            modify = tripService.modify(trip, delImgIds); // db 에서 삭제
        } catch (Exception e) {
            log.error(e.getMessage());
            msg+="에러:"+e.getMessage();
        }
        if (modify > 0) { // 수정성공
            if (imgDtos == null || imgDtos.size() < 1) {
                redirectAttributes.addFlashAttribute("msg", "이미지는 최소 1개 이상이어야 합니다.");
                return redirectPage;
            }else { // 삭제할 이미지 있으면
                    for (TripImgDto ti : imgDtos) {
                        File imgFile = new File(staticPath + ti.getImgPath());
                        if (imgFile.exists()) imgFile.delete(); // 실제 삭제
                    }
            }
            msg="수정성공";
            redirectPage = "redirect:/trip/list.do";
        }

        redirectAttributes.addFlashAttribute("msg",msg);
        return redirectPage;
    }


    @GetMapping("/{tId}/detail.do")
    public String detail(Model model, @PathVariable int tId) {
        TripDto trip = tripService.detail(tId);
        model.addAttribute("t", trip);
        return "/trip/detail";
    }


    @GetMapping("/register.do")
    public void registerForm(
//            @SessionAttribute UserDto loginUser
    ) {
    }

    @PostMapping("/register.do")
    public String registerAction(
//            @SessionAttribute UserDto loginUser, // 글쓴이와 로그인한 사람 같은지 확인예정
            @ModelAttribute TripDto trip,
            RedirectAttributes redirectAttributes,
            @RequestParam(name = "img", required = false) MultipartFile[] imgs) { // required=false : 파라미터 img 없어도 에러발생안하도록!
        String redirectPage = "redirect:/trip/register.do";
//        if(!loginUser.getUId().equals(trip.getUId())) return redirectPage; // 다르면 다시 등록페이지로 이동
        String msg="";

        // 제목 입력 여부 확인
        if (trip.getTitle() == null || trip.getTitle().equals("")) {
            msg = "제목을 입력하세요.";
            redirectAttributes.addFlashAttribute("msg",msg);
            return redirectPage;
        }

        List<TripImgDto> imgDtos=null;
        if (imgs != null) {
            imgDtos = new ArrayList<>();
            for (MultipartFile img : imgs) {
                if (!img.isEmpty()) {
                    String[] contentTypes = img.getContentType().split("/");
                    if (contentTypes[0].equals("image")) {
                        String fileName = System.currentTimeMillis() + "_" + (int) (Math.random() * 10000) + "." + contentTypes[1];
                        Path path = Paths.get(uploadPath + "/trip/" + fileName);
                        try {
                            img.transferTo(path);
                        } catch (IOException e) {
                            log.error(e.getMessage());
                        }
                        TripImgDto imgDto = new TripImgDto();
                        imgDto.setImgPath("/public/img/trip/" + fileName);
                        imgDtos.add(imgDto);

                        if (imgDtos != null && imgDtos.size() > 0) {
                            imgDtos.get(0).setImgMain(true);
                        }
                    }
                }
            }
        } else { // imgs 가 null 이면
            msg="이미지를 등록하세요.";
            redirectAttributes.addFlashAttribute("msg",msg);
            return redirectPage;
        }
        trip.setImgs(imgDtos);
        int register = 0;
        try {
            register = tripService.register(trip);
        } catch (Exception e) {
            log.error(e.getMessage());
            msg=e.getMessage();
            redirectAttributes.addFlashAttribute("msg",msg);
        }
        if (register > 0) { // 등록성공
            if (imgDtos.size() < 1) {
                redirectAttributes.addFlashAttribute("msg", "이미지는 최소 1개 이상이어야 합니다.");
                return redirectPage;
            }
            redirectPage = "redirect:/trip/list.do";

        } else { // 등록실패 -> 파일삭제하기
            if (imgDtos != null) { // 이미지가 null 이 아니면
                for (TripImgDto imgDto : imgDtos) {
                    File imgFile = new File(staticPath + imgDto.getImgPath());
                    if (imgFile.exists()) imgFile.delete(); // 파일삭제
                }
            }
        }
        return redirectPage;
    }


    @GetMapping("/list.do")
    public String list(Model model
//                       @SessionAttribute(required = false) UserDto loginUser
    ) {
        List<TripDto> trips;
        trips = tripService.list();
        model.addAttribute("trips", trips);
        return "/trip/list";
    }


}

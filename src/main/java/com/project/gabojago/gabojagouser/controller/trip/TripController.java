package com.project.gabojago.gabojagouser.controller.trip;

import com.github.pagehelper.PageInfo;
import com.project.gabojago.gabojagouser.dto.trip.TripDto;
import com.project.gabojago.gabojagouser.dto.trip.TripImgDto;
import com.project.gabojago.gabojagouser.dto.trip.TripPageDto;
import com.project.gabojago.gabojagouser.dto.trip.TripReviewDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.trip.TripReviewService;
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

    @Value("${static.path}")
    private String staticPath;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }


    @GetMapping("/{tId}/remove.do") // db 정보 삭제 + 이미지 실제 삭제
    public String removeAction(
            @PathVariable int tId,
            @SessionAttribute UserDto loginUser,
        RedirectAttributes redirectAttributes){
        String redirectPage="redirect:/trip/list.do";
        String msg="삭제실패";
        TripDto trip=null;
        List<TripImgDto> imgDtos=null;
        int remove=0;
        try{
            // 파라미터 tId 로 detail 정보를 db 에서 불러온다
            trip=tripService.detail(tId,loginUser);
            imgDtos=trip.getImgs();
            remove=tripService.remove(tId,imgDtos);
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
            @PathVariable int tId,
            @SessionAttribute UserDto loginUser
    ) {
        log.info(staticPath);
        System.out.println("staticPath = " + staticPath);
        TripDto trip = tripService.detail(tId,loginUser);
        model.addAttribute("t", trip);
        return "/trip/modify";
    }


    @PostMapping("/modify.do")
    public String modifyAction(
            @ModelAttribute TripDto trip,
            @RequestParam (required = false)MultipartFile mainImg, // 메인이미지 파라미터
            @RequestParam(name="img", required = false) List<MultipartFile> imgs,
            @RequestParam(name="delImgId", required = false) List<Integer> delImgIds,
            @RequestParam(name = "delMainImgId",required = false) int delMainImgId,
            @RequestParam(name="tag", required = false) List<String>tags,
            @RequestParam(name="delTag", required = false) List<String> delTags,
            RedirectAttributes redirectAttributes
    ) throws IOException {
        // requestParam : name 이 맞다. value 는 value 값을 미리 지정하는것.
        String redirectPage = "redirect:/trip/" + trip.getTId() + "/modify.do";
        String msg="";

        if(!mainImg.isEmpty()){ // 메인이미지가 있을때
            if(imgs==null)imgs=new ArrayList<>(); // 서브이미지가 없으면
            imgs.add(mainImg);
            if(delImgIds==null) {
                delImgIds=new ArrayList<>();
                delImgIds.add(delMainImgId);
            }
        }

        // 제목 입력 여부 확인
        if (trip.getTitle() == null || trip.getTitle().equals("")) {
            msg = "제목을 입력하세요.";
            redirectAttributes.addFlashAttribute("msg",msg);
            return redirectPage;
        }
        List<TripImgDto> imgDtos = null;
        if(imgs!=null){
            imgDtos=new ArrayList<>();
            for (int i=0; i<imgs.size(); i++) {
                MultipartFile img=imgs.get(i);
                if (!img.isEmpty()) {
                    String[] contentTypes = img.getContentType().split("/");
                    if (contentTypes[0].equals("image")) {
                        log.info(img.getOriginalFilename());
                        System.out.println("staticPath = " + staticPath);
                        System.out.println("img.getOriginalFilename() = " + img.getOriginalFilename());
                        String fileName = System.currentTimeMillis() + "_" + (int) (Math.random() * 10000) + "." + contentTypes[1];
                        Path path = Paths.get(staticPath + "/public/img/trip/" + fileName);
                        img.transferTo(path);
                        TripImgDto imgDto = new TripImgDto();
                        if(!mainImg.isEmpty() && i==imgs.size()-1)imgDto.setImgMain(true);
                        imgDto.setTId(trip.getTId());
                        imgDto.setImgPath("/public/img/trip/" + fileName);
                        imgDtos.add(imgDto); // 이미지는 마지막에 저장하기
                        log.info(imgDto);
                    }
                }
            }
        }
        trip.setImgs(imgDtos);
        List<TripImgDto> delImgDtos=null; // 삭제할 이미지 리스트
        int modify = 0;
        msg="등록실패";
        try {
            if (delImgIds != null) delImgDtos = tripService.imgList(delImgIds); // 삭제할 이미지아이디가 있으면 => 수정
            modify =  tripService.modify(trip,delImgIds,tags,delTags); // db 에서 삭제
        } catch (Exception e) {
            log.error(e.getMessage());
            msg+="에러:"+e.getMessage();
        }
        if (modify > 0) { // 수정성공
           if(delImgDtos!=null) { // 삭제할 이미지 있으면
                    for (TripImgDto ti : delImgDtos) {
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
    public String detail(Model model,
                         @PathVariable int tId,
                         @SessionAttribute(required = false) UserDto loginUser) {
        TripDto trip = tripService.detail(tId,loginUser);

        String urlAddress=trip.getUrlAddress();
        model.addAttribute("t", trip);
        model.addAttribute("urlAddress",urlAddress);
        return "/trip/detail";
    }


    @GetMapping("/register.do")
    public void registerForm(
            @SessionAttribute UserDto loginUser) {}

    @PostMapping("/register.do")
    public String registerAction(
            @SessionAttribute UserDto loginUser, // 글쓴이와 로그인한 사람 같은지 확인예정
            @ModelAttribute TripDto trip,
            @RequestParam MultipartFile mainImg, // 메인이미지 파라미터
            @RequestParam(name = "img", required = false) List<MultipartFile> imgs,
            @RequestParam(name="tag", required = false) List<String> tags,
            RedirectAttributes redirectAttributes
            ) { // required=false : 파라미터 img 없어도 에러발생안하도록!
        String redirectPage = "redirect:/trip/register.do";
        if(!loginUser.getUId().equals(trip.getUId())) return redirectPage; // 다르면 다시 등록페이지로 이동
        String msg="";

        // 제목 입력 여부 확인
        if (trip.getTitle() == null || trip.getTitle().equals("")) {
            msg = "여행지명을 입력하세요.";
            redirectAttributes.addFlashAttribute("msg",msg);
            return redirectPage;
        }

        if(imgs==null){ // 이미지 등록이 없을때, 이미지 리스트 배열 생성 초기화
            imgs = new ArrayList<>();
        }

        if (mainImg.isEmpty()) {
            msg = "메인 이미지를 등록하세요.";
            redirectAttributes.addFlashAttribute("msg", msg);
            return redirectPage;
        }

        if (!mainImg.isEmpty()) { // 이미지 등록하면
            imgs.add(mainImg);
        }

        if (imgs.size()<2) {
            msg = "서브 이미지 1개 이상 등록하세요.";
            redirectAttributes.addFlashAttribute("msg", msg);
            log.info(imgs);
            System.out.println("imgs = " + imgs);
            for(MultipartFile img : imgs){
                System.out.println("img.getOriginalFilename() = " + img.getOriginalFilename());
            }
            return redirectPage;
        }

        List<TripImgDto> imgDtos=null;
        if (imgs != null) {
            imgDtos = new ArrayList<>();
            for (int i=0; i<imgs.size(); i++) {
                MultipartFile img=imgs.get(i);
                if (!img.isEmpty()) {
                    String[] contentTypes = img.getContentType().split("/");
                    if (contentTypes[0].equals("image")) {
                        String fileName = System.currentTimeMillis() + "_" + (int) (Math.random() * 10000) + "." + contentTypes[1];
                        Path path = Paths.get(staticPath + "/public/img/trip/" + fileName);
                        try {
                            img.transferTo(path);
                        } catch (IOException e) {
                            log.error(e.getMessage());
                        }
                        TripImgDto imgDto = new TripImgDto();
                        if(i==imgs.size()-1)imgDto.setImgMain(true); // 인덱스 마지막일때 이미지 => 메인 이미지
                        imgDto.setImgPath("/public/img/trip/" + fileName);
                        imgDtos.add(imgDto);
                    }
                }
            }
        }
        trip.setImgs(imgDtos);
        int register = 0;
        try {
            register = tripService.register(trip,tags);
        } catch (Exception e) {
            log.error(e.getMessage());
//            msg="핸드폰 번호는 중복불가. 다시 입력해주세요";
//            redirectAttributes.addFlashAttribute("msg",msg);
        }
        if (register > 0) { // 등록성공
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
    public String list(Model model,
                       @SessionAttribute(required = false) UserDto loginUser,
                       TripPageDto pageDto) {
        List<TripDto> trips;
        trips = tripService.list(loginUser,pageDto);
        PageInfo<TripDto> pageTrips=new PageInfo<>(trips);
        model.addAttribute("page",pageTrips);
        model.addAttribute("trips", trips);
        return "/trip/list";
    }

    @GetMapping("/{tag}/tagList.do")
    public String tagList(
            @PathVariable String tag,
            Model model,
            @SessionAttribute(required = false) UserDto loginUser,
            TripPageDto pageDto){
       List<TripDto> trips;
       pageDto.setPageSize(4);
       trips=tripService.tagList(tag,loginUser,pageDto);
       model.addAttribute("trips",trips);
       model.addAttribute("tag",tag);
       return "/trip/tagList";
    }

    @GetMapping("/{tag}/ajaxTagList.do")
    public String ajaxTagList(
            @PathVariable String tag,
            Model model,
            @SessionAttribute(required = false)UserDto loginUser,
            TripPageDto pageDto){
        List<TripDto> trips;
        pageDto.setPageSize(4);
        return "/trip/includeList";
    }

}

package com.project.gabojago.gabojagouser.controller.sells;
import com.github.pagehelper.PageInfo;
import com.project.gabojago.gabojagouser.dto.sells.*;
import com.project.gabojago.gabojagouser.dto.trip.TripImgDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.sells.SellBookMarksService;
import com.project.gabojago.gabojagouser.service.sells.SellsService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
//@AllArgsConstructor
@RequestMapping("/sells")
@Log4j2
public class SellsController {
    private SellsService sellsService;
    private SellBookMarksService sellBookMarksService;
    public SellsController(SellsService sellsService,SellBookMarksService sellBookMarksService) {
        this.sellBookMarksService=sellBookMarksService;
        this.sellsService = sellsService;
    }
    @Value("${img.upload.path}")
    private String uploadPath;
    @Value("${static.path}")
    private String staticPath;

    private final int MAX_IMAGE_SIZE = 1024 * 1024;
    private final int MAX_PULL_IMAGE_SIZE = 5 * MAX_IMAGE_SIZE;
    @RequestMapping("/{title}/search.do")
    public String searchSells(@PathVariable String title,
                              Model model,
                              @ModelAttribute SellPageDto pageDto
                              ){



        List<SellsDto> sells;
        sells=sellsService.findByTitle(title);
        PageInfo<SellsDto> pageSells=new PageInfo<>(sells);
        model.addAttribute("page",pageSells);
        model.addAttribute("sells",sells);
        return "/sells/list";
    }
    @GetMapping("/{category}/list.do")
    public String categoryList(@PathVariable String category,
                               Model model,
                               @ModelAttribute SellPageDto pageDto){
        List<SellsDto> sells;
        sells=sellsService.findByCategory(category,pageDto);
        PageInfo<SellsDto> pageSells=new PageInfo<>(sells);
        model.addAttribute("page",pageSells);
        model.addAttribute("sells",sells);
        return "/sells/list";
    }
    @GetMapping("list.do")
    public String list(Model model,
                       @ModelAttribute SellPageDto pageDto){
        List<SellsDto> sells;

           sells=sellsService.List(pageDto);

//        System.out.println("sells = " + sells);
        PageInfo<SellsDto> pageSells=new PageInfo<>(sells);
            model.addAttribute("page",pageSells);
            model.addAttribute("sells",sells);
        return "/sells/list";

    }
    @GetMapping("/{sId}/detail.do")
    public String detail(Model model,@PathVariable int sId){
            SellsDto sells=sellsService.detail(sId);
//        List<SellsDto> sells;
//        sells=sellsService.List();
        model.addAttribute("sells",sells);
        return "/sells/detail";
    }
    @GetMapping("/{sId}/modify.do")
    public String modifyForm(Model model,
            @PathVariable int sId
    ){
        SellsDto sell=sellsService.detail(sId);
        model.addAttribute("sell",sell);
        return "/sells/modify";
    }

    @PostMapping("/modify.do")
    public String modifyAction2(@ModelAttribute SellsDto sell,
                                @ModelAttribute SellImgsDto sellImg,
                               @RequestParam(name = "delImgId",required = false)List<Integer> delImgIds,
                               @RequestParam(name = "img",required = false)List<MultipartFile>  imgs,
                               @RequestParam(name = "mainImg",required = false)MultipartFile mainImg,
                               @RequestParam(name = "delOption",required = false)int [] delOptionIds,
                               @RequestParam(value="name",required = false) String[] name,
                               @RequestParam(value="price",required = false) int[] price,
                               RedirectAttributes redirectAttributes
    ) throws IOException {
        String msg = "";
        String redirecPath = "redirect:/sells/" + sell.getSId() + "/modify.do";
        List<SellImgsDto> imgDtos = null;
        List<SellsOptionDto> sellsOptions = null;
                        imgs.add(mainImg);


        if (mainImg==null){
            sell.setImgMain(sell.getImgMain());
        }
        imgDtos = new ArrayList<>();
        for (int i = 0; i < imgs.size(); i++) {
            MultipartFile img = imgs.get(i);
            if (img != null && !img.isEmpty()) { // 이미지가 null이 아니고 비어있지 않은 경우
                String[] contentTypes = img.getContentType().split("/");
                if (contentTypes[0].equals("image")) {
                    String fileName = System.currentTimeMillis() + "_" + (int) (Math.random() * 10000) + "." + contentTypes[1];
                    Path path = Paths.get(uploadPath + "/sells/" + fileName);
                    try {
                        img.transferTo(path);
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }

                    SellImgsDto imgDto = new SellImgsDto();
                    if (i == imgs.size() - 1) {
                        sell.setImgMain("/public/img/sells/" + fileName);
                        imgDto.setImgPath("/public/img/sells/" + fileName);
                        imgDtos.add(imgDto);
                    } else {
                        imgDto.setImgPath("/public/img/sells/" + fileName);
                        imgDtos.add(imgDto);
                    }
                }
            }
        }

        if (name != null && price != null) {
            sellsOptions = new ArrayList<>();
            for (int i = 0; i < name.length; i++) {
                SellsOptionDto sellsOption = new SellsOptionDto();
                sellsOption.setName(name[i]);
                sellsOption.setPrice(price[i]);
                sellsOptions.add(sellsOption);
            }
        }
            sell.setSellImgs(imgDtos);
            sell.setSellOption(sellsOptions);
            int modify = 0;
            try {
                if (delImgIds != null) imgDtos=  sellsService.imgList(delImgIds); // 삭제할 이미지아이디가 있으면 => 수정
                modify =sellsService.modify(sell, delImgIds, delOptionIds); // db 에서 삭제
                msg="수정 성공하였습니다.";
                if (delImgIds!=null){
                    for (SellImgsDto img : imgDtos){
                    File imgFile=new File(staticPath +img.getImgPath());
                    if (imgFile.exists()) imgFile.delete();
                }
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                msg += "에러:" + e.getMessage();


            }
            redirectAttributes.addFlashAttribute("msg", msg);
            return "redirect:/sells/list.do";
    }

    @GetMapping("/register.do")
    public void insertForm(){
    }
    @PostMapping("/register.do")
    public String registerAction2(@ModelAttribute SellsDto sell,
                                  @RequestParam(required = false) MultipartFile mainImg,
                                  @RequestParam(value = "img",required = false) MultipartFile sImg,
                                 @RequestParam(value = "img",required = false)List<MultipartFile> imgs,
                                 @RequestParam(value="name",required = false) String[] name,
                                 @RequestParam(value="price",required = false) int[] price,
                                 RedirectAttributes redirectAttributes) throws IOException {

    String redirectPage = "redirect:/sells/register.do";
    //        if(!loginUser.getUId().equals(trip.getUId())) return redirectPage; // 다르면 다시 등록페이지로 이동
    String msg="";


    imgs.add(mainImg);

    List<SellImgsDto> imgDtos=null;
    List<SellsOptionDto> sellsOptions=null;



        if (name == null ||name.length==0) {
            msg="상품명을 입력하세요";
            redirectAttributes.addFlashAttribute("msg",msg);
            return redirectPage;
        }


        if (price ==null||price.length==0) {
            msg="상품 가격을 입력하세요";
            redirectAttributes.addFlashAttribute("msg",msg);
            return redirectPage;
        }

        if (mainImg==null||mainImg.isEmpty() ){
            msg="메인 이미지는 1개 꼭 등록하셔야 합니다.";
            redirectAttributes.addFlashAttribute("msg", msg);
            return redirectPage;
        }

        if (mainImg.getSize() > MAX_IMAGE_SIZE) {
            msg="메인이미지의 최대 크기는"+MAX_IMAGE_SIZE/(1024 * 1024)+"MB 입니다";
            redirectAttributes.addFlashAttribute("msg",msg);
            return redirectPage;
        }


         // 메인이미지 추가
    // 제목 입력 여부 확인
        if (sImg==null || sImg.isEmpty() ){
            msg="서브용 이미지는 1개이상 등록하셔야 합니다.";
            redirectAttributes.addFlashAttribute("msg", msg);
            return redirectPage;
        }
        if (sell.getTitle() == null || sell.getTitle().equals("")) {
        msg = "상품명을 입력하세요.";
        redirectAttributes.addFlashAttribute("msg",msg);
        return redirectPage;
    }



        imgDtos = new ArrayList<>();
        for (int i = 0; i < imgs.size(); i++) {
            MultipartFile img = imgs.get(i);
            if (img != null && !img.isEmpty()) { // 이미지가 null이 아니고 비어있지 않은 경우
                String[] contentTypes = img.getContentType().split("/");
                if (contentTypes[0].equals("image")) {
                    String fileName = System.currentTimeMillis() + "_" + (int) (Math.random() * 10000) + "." + contentTypes[1];
                    Path path = Paths.get(uploadPath + "/sells/" + fileName);
                    try {
                        img.transferTo(path);
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }

                    SellImgsDto imgDto = new SellImgsDto();
                    if (i == imgs.size() - 1) {
                        sell.setImgMain("/public/img/sells/" + fileName);
                        imgDto.setImgPath("/public/img/sells/" + fileName);
                        imgDtos.add(imgDto);
                    } else {
                        imgDto.setImgPath("/public/img/sells/" + fileName);
                        imgDtos.add(imgDto);
                    }
                }
            }else if(img==null ||img.isEmpty()) {
                msg = "이미지는 1개 이상 등록하세요.";
                redirectAttributes.addFlashAttribute("msg", msg);
                return redirectPage;
            }
        }

            sellsOptions=new ArrayList<>();
            for (int i = 0; i < name.length; i++) {
                SellsOptionDto sellsOption=new SellsOptionDto();
                sellsOption.setName(name[i]);
                sellsOption.setPrice(price[i]);
                sellsOptions.add(sellsOption);

            }
        sell.setSellImgs(imgDtos);
            sell.setSellOption(sellsOptions);
    int register = 0;
        try {
        register = sellsService.register(sell);
            msg="게시글 등록 완료!";
    } catch (Exception e) {
        log.error(e.getMessage());
        msg=e.getMessage();

    }
        if (register > 0) { // 등록성공
        if (imgDtos.size() < 1) {
            redirectAttributes.addFlashAttribute("msg", "이미지는 최소 1개 이상이어야 합니다.");
            return redirectPage;
        }
        redirectPage = "redirect:/sells/list.do";

    } else { // 등록실패 -> 파일삭제하기
        if (imgDtos != null) { // 이미지가 null 이 아니면
            for (SellImgsDto imgDto : imgDtos) {
                File imgFile = new File(staticPath + imgDto.getImgPath());
                if (imgFile.exists()) imgFile.delete(); // 파일삭제
            }
        }
    }

        redirectAttributes.addFlashAttribute("msg",msg);
        return redirectPage;
}

    @GetMapping("/{sId}/remove.do")
    public String removeAction(@PathVariable int sId,
                               RedirectAttributes redirectAttributes){
        String redirecPath="redirect:/sells/"+sId+"/modify.do";
        String msg="";
        SellsDto sells=null;
        List<SellImgsDto> imgs=null;
        int remove=0;
        try {
        sells=sellsService.detail(sId);
        imgs=sells.getSellImgs();
        remove=sellsService.remove(sId);

        }catch (Exception e){
            log.error(e.getMessage());
        }
        if (remove>0){
            if (imgs!=null){
                for (SellImgsDto img:imgs){
                    File imgFile=new File(staticPath+img.getImgPath());
                    if (imgFile.exists())imgFile.delete();
                }
            }
            msg="삭제 성공!";
            redirectAttributes.addFlashAttribute("msg",msg);
            redirecPath="redirect:/sells/list.do";

        }

        return redirecPath;
    }


    @GetMapping("/sellBook/list.do")
    public String list(@SessionAttribute UserDto loginUser,
                       Model model){
        System.out.println("loginUser = " + loginUser);
        List<SellBookmarksDto> list=sellBookMarksService.List(loginUser.getUId());
        model.addAttribute("list",list);
        return "/sells/sellBookList";
    }
    @GetMapping("/{sbId}/{sId}/remove.do")
    public String sellBookRemoveAction(@PathVariable int sbId,
                                        @PathVariable int sId,
                                        RedirectAttributes redirectAttributes,
                                        Model model){
        int remove=0;
        String msg="";
        String redirectPath="redirect:/sells/sellBook/list.do";
        remove=sellBookMarksService.remove(sbId);
        if (remove>0){
            msg="북마크가 삭제 되었습니다.";
        }else {
            msg="북마크 삭제 실패!! 다시 시도하세요";
        }
        redirectAttributes.addFlashAttribute("msg","북마크가 삭제되었습니다.");
        return redirectPath;

    }


}

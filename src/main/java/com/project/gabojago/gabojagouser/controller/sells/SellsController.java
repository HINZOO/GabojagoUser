package com.project.gabojago.gabojagouser.controller.sells;
import com.github.pagehelper.PageInfo;
import com.project.gabojago.gabojagouser.dto.sells.*;
import com.project.gabojago.gabojagouser.dto.trip.TripImgDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.sells.SellBookMarksService;
import com.project.gabojago.gabojagouser.service.sells.SellsService;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
//                               @RequestParam(name = "delImgMainId",required = false)Integer delImgMainId,
                               @RequestParam(name = "delImgId",required = false)List<Integer> delImgIds,
                               @RequestParam(name = "img",required = false)List<MultipartFile>  imgs,
                               @RequestParam(name = "mainImg",required = false)MultipartFile mainImg,
                               @RequestParam(name = "delOption",required = false)int [] delOptionIds,
                               @RequestParam(value="name",required = false) String[] name,
                               @RequestParam(value="price",required = false) int[] price,
                               RedirectAttributes redirectAttributes
    ) {
//        System.out.println("delImgMainId = " + delImgMainId);
        String msg = "";
        String redirecPath = "redirect:/sells/" + sell.getSId() + "/modify.do";
            imgs.add(mainImg);
//            delImgIds.add(delImgMainId);
        List<SellImgsDto> imgDtos = null;
        List<SellsOptionDto> sellsOptions = null;

        imgDtos = new ArrayList<>();
        for (int i = 0; i < imgs.size(); i++) {
            MultipartFile img = imgs.get(i);
            if (img != null && !img.isEmpty()) { // 이미지가 null이 아니고 비어있지 않은 경우
                String[] contentTypes = img.getContentType().split("/");
                if (contentTypes[0].equals("image")) {
                    String fileName = System.currentTimeMillis() + "_" + (int) (Math.random() * 10000) + "." + contentTypes[1];
                    Path path = Paths.get(uploadPath + "/sell/" + fileName);
                    try {
                        img.transferTo(path);
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }

                    SellImgsDto imgDto = new SellImgsDto();
                    if (i == imgs.size() - 1) {
                        sell.setImgMain("/public/img/sell/" + fileName);
                    } else {
                        imgDto.setImgPath("/public/img/sell/" + fileName);
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
//        sell.setImgMain(delImgMainId);
            sell.setSellOption(sellsOptions);
            int modify = 0;
            try {
                if (delImgIds != null) imgDtos=  sellsService.imgList(delImgIds); // 삭제할 이미지아이디가 있으면 => 수정
                modify =sellsService.modify(sell, delImgIds, delOptionIds); // db 에서 삭제
                msg="수정 성공하였습니다.";
            } catch (Exception e) {
                log.error(e.getMessage());
                msg += "에러:" + e.getMessage();


            }
            if (imgDtos!=null){
                for (SellImgsDto img : imgDtos){
                    File imgFile=new File(staticPath +img.getImgPath());
                    if (imgFile.exists()) imgFile.delete();
                }
            }
//            if (modify > 0) { // 수정성공
//                if (imgDtos == null || imgDtos.size() < 1) {
//                    redirectAttributes.addFlashAttribute("msg", "이미지는 최소 1개 이상이어야 합니다.");
//                    return redirecPath;
//                } else { // 삭제할 이미지 있으면
//                    for (SellImgsDto img : imgDtos) {
//                        File imgFile = new File(staticPath + img.getImgPath());
//                        if (imgFile.exists()) imgFile.delete(); // 실제 삭제
//                    }
//                }
//                msg = "수정성공";
//            }

            redirectAttributes.addFlashAttribute("msg", msg);
            return "redirect:/sells/list.do";
    }
//    @PostMapping("/modify.do")
//    public String modifyAction(@ModelAttribute SellsDto sell,
//                               @RequestParam(name = "delImgId",required = false)int [] delImgIds,
//                               @RequestParam(name = "img",required = false)MultipartFile [] imgs,
//                               @RequestParam(name = "delOption",required = false)int [] delOptionIds,
//                               @RequestParam(value="name",required = false) String[] name,
//                               @RequestParam(value="price",required = false) int[] price,
//                               RedirectAttributes redirectAttributes
//                               ) {
//        String redirectPage="redirect:/sells/"+sell.getSId()+"/modify.do";
//        List<SellImgsDto> imgsDtos=null;
//        int modify=0;
//        String msg="";
//        try {
//
//            if(delImgIds!=null)imgsDtos=sellsService.imgList(delImgIds);
//            //삭제 전에 이미지 파일 경로를 받아옴
//            modify=sellsService.modify(sell,delImgIds,delOptionIds);
//            if (imgs!=null){
//                for(MultipartFile img : imgs){
//                    if(!img.isEmpty()) {
//                        String[] contentTypes = img.getContentType().split("/"); // text/xml application/json image/png
//                        if (contentTypes[0].equals("image")) {
//                            String fileName =  +System.currentTimeMillis() + "_" + (int) (Math.random() * 10000) + "." + contentTypes[1];
//                            Path path = Paths.get(uploadPath+"/sell/"+ fileName);
//                            img.transferTo(path);
//                            SellImgsDto imgDto = new SellImgsDto();
//                            imgDto.setImgPath("/public/img/sell/" + fileName);
//                            imgDto.setSId(sell.getSId());
//                            sellsService.imgRegister(imgDto);
//                        }
//                    }
//                }
//            }
//            if (name!=null && price!=null) {
//                for (int i = 0; i < name.length; i++) {
//                    SellsOptionDto sellsOption = new SellsOptionDto();
//                    sellsOption.setSId(sell.getSId());
//                    sellsOption.setName(name[i]);
//                    sellsOption.setPrice(price[i]);
//                    sellsService.optionRegister(sellsOption);
//                }
//            }
//
//
//
//
//
//        }catch (Exception e){
//            log.error(e.getMessage());
//        }
//        if(modify>0){
//            if (imgsDtos!=null){
//                for (SellImgsDto i : imgsDtos){
//                    File imgFile=new File(staticPath+i.getImgPath());
//                    if(imgFile.exists())imgFile.delete();
//                }
//            }
//            redirectAttributes.addFlashAttribute("msg","수정성공");
//            redirectPage="redirect:/sells/list.do";
//        }
//        return redirectPage;
//    }
//

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


         // 메인이미지 추가
    // 제목 입력 여부 확인
        if (sImg==null || sImg.isEmpty() || sImg.getSize() > 5 * 1024 * 1024){
            msg="서브용 이미지는 1개이상 등록하셔야 합니다.";
            redirectAttributes.addFlashAttribute("msg", msg);
            return redirectPage;
        }
        if (mainImg==null||mainImg.isEmpty() ||sImg.getSize() > 5 * 1024 * 1024){
            msg="메인 이미지는 1개 꼭 등록하셔야 합니다.";
            redirectAttributes.addFlashAttribute("msg", msg);
            return redirectPage;
        }
        if (sell.getTitle() == null || sell.getTitle().equals("")) {
        msg = "상품명을 입력하세요.";
        redirectAttributes.addFlashAttribute("msg",msg);
        return redirectPage;
    }
        if (imgs == null || imgs.isEmpty()) { // 이미지가 null이거나 비어있는 경우
            msg = "이미지는 1개 이상 등록하세요.";
            redirectAttributes.addFlashAttribute("msg", msg);
            return redirectPage;
        }

        imgDtos = new ArrayList<>();
        for (int i = 0; i < imgs.size(); i++) {
            MultipartFile img = imgs.get(i);
            if (img != null && !img.isEmpty()) { // 이미지가 null이 아니고 비어있지 않은 경우
                String[] contentTypes = img.getContentType().split("/");
                if (contentTypes[0].equals("image")) {
                    String fileName = System.currentTimeMillis() + "_" + (int) (Math.random() * 10000) + "." + contentTypes[1];
                    Path path = Paths.get(uploadPath + "/sell/" + fileName);
                    try {
                        img.transferTo(path);
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }

                    SellImgsDto imgDto = new SellImgsDto();
                    if (i == imgs.size() - 1) {
                        sell.setImgMain("/public/img/sell/" + fileName);
                    } else {
                        imgDto.setImgPath("/public/img/sell/" + fileName);
                        imgDtos.add(imgDto);
                    }
                }
            }
        }
        if (name==null||price==null){
            msg="옵션은 1개이상 등록하세요";
            redirectAttributes.addFlashAttribute("msg", msg);
            return redirectPage;
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

//        @Transactional
//    @PostMapping("/register.do")
//    public String registerAction(@ModelAttribute SellsDto sell,
//                                 @ModelAttribute SellsOptionDto sellsOptionDto,
//                                 @RequestParam(value = "img",required = false)MultipartFile [] imgs,
//                                 @RequestParam(value="name",required = false) String[] name,
//                                 @RequestParam(value="price",required = false) int[] price,
//                                 RedirectAttributes redirectAttributes) throws IOException {
//
//        String redirectPage="redirect:/sells/register.do";
//        String msg="";
//
//        System.out.println("sell입니다 = " + sell);
//        if(name == null || price == null) { // 옵션 없으면
//            msg = "옵션은 최소 1개이상 추가해 주세요";
//            redirectAttributes.addFlashAttribute("msg", msg);
//            return redirectPage;
//        }else if (imgs==null){ // imgs 가 null 이면 msg
//            msg="이미지를 등록해 주세요.";
//            redirectAttributes.addFlashAttribute("msg",msg);
//            return redirectPage;
//        }else {
//            sellsService.register(sell);
//            for(MultipartFile img : imgs){
//                if(!img.isEmpty()) {
//                    String[] contentTypes = img.getContentType().split("/"); // text/xml application/json image/png
//                    if (contentTypes[0].equals("image")) {
//                        String fileName =  +System.currentTimeMillis() + "_" + (int) (Math.random() * 10000) + "." + contentTypes[1];
//                        Path path = Paths.get(uploadPath+"/sell/"+ fileName);
//                        img.transferTo(path);
//                        SellImgsDto imgDto = new SellImgsDto();
//                        imgDto.setImgPath("/public/img/sell/" + fileName);
//                        imgDto.setSId(sell.getSId());
//                        sellsService.imgRegister(imgDto);
//                    }
//                }
//            }
//        }
//
//            // 옵션 등록
//        for (int i = 0; i < name.length; i++) {
//            SellsOptionDto sellsOption = new SellsOptionDto();
//            sellsOption.setSId(sell.getSId());
//            sellsOption.setName(name[i]);
//            sellsOption.setPrice(price[i]);
//            System.out.println("sellsOption = " + sellsOption);
//            sellsService.optionRegister(sellsOption);
//        }
//
//           return redirectPage="redirect:/sells/list.do";
//
//    }
    @GetMapping("/{sId}/remove.do")
    public String removeAction(@PathVariable int sId){
        String redirecPath="redirect:/sells/"+sId+"/modify.do";
        sellsService.remove(sId);
        return "redirect:/sells/list.do";
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

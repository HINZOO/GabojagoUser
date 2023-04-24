package com.project.gabojago.gabojagouser.controller.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanCheckListsDto;
import com.project.gabojago.gabojagouser.dto.plan.PlanDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.mapper.plan.PlanMapper;
import com.project.gabojago.gabojagouser.service.plan.PlanCheckListsService;
import com.project.gabojago.gabojagouser.service.plan.PlanService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.channels.Pipe;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/plan")
@Log4j2
public class PlanController {
    private PlanService planService;
    private PlanCheckListsService planCheckListsService;
    @Value("${static.path}")
    private String staticPath;

    public PlanController(PlanService planService, PlanCheckListsService planCheckListsService) {
        this.planService = planService;
        this.planCheckListsService = planCheckListsService;
    }

    @GetMapping("/list.do")
    public String list(
            @SessionAttribute(required = false) UserDto loginUser,
            Model model)
    {
        if (loginUser!=null){
            List<PlanDto> plans = planService.list(loginUser.getUId());
            model.addAttribute("plans", plans);
            return "/plan/list";
        } else {
            //오토로그인 안돼서 임시로 해둠
            String uId = "USER01";
            List<PlanDto> plans = planService.list(uId);
            model.addAttribute("plans", plans);
            return "/plan/list";
        }
    }

    @PostMapping("/insert.do")
    public String insert(
            @SessionAttribute UserDto loginUser,
            PlanDto planDto,
            @RequestParam(value ="img",required = false) MultipartFile[] img) throws IOException
    {
//        String redirectPage="redirect:/plan/list.do";
//        if(!loginUser.getUId().equals(planDto.getUId())) return redirectPage;
        log.info("플랜테스트"+img);
        if (img != null) {
            String[] contentTypes = img[0].getContentType().split("/");
            if (contentTypes[0].equals("image")) {
                String fileName = System.currentTimeMillis() + "_" + (int) (Math.random() * 10000) + "." + contentTypes[1];
                Path path = Paths.get(staticPath + "/public/img/plan/" + fileName);
                img[0].transferTo(path);
                planDto.setImgPath("/public/img/plan/" + fileName);
            }
        }
        int register = 0;
        try {
            register =planService.register(planDto);
        }catch (Exception e){
            log.error("새 플랜 등록 오류 : "+e.getMessage());
        }
        if (register>0){
            return "redirect:/plan/" + planDto.getPId()+ "/detail.do";
        } else {
            File imgFile = new File(staticPath + planDto.getImgPath());
            if (imgFile.exists()) {
                imgFile.delete();
            }
            return "redirect:/plan/list.do";
        }
    }
    @GetMapping("/{pId}/detail.do")
    public String detail(@PathVariable int pId, Model model) throws ParseException { // 플랜 detail 보기
        PlanDto plan = planService.detail(pId);

        // 여행 기간이 여러 날인 경우 분리해서 렌더링 하기 위함
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = format.parse(plan.getPlanFrom());
        Date date2 = format.parse(plan.getPlanTo());
        long gap = ((date2.getTime()-date1.getTime())/(24*60*60*1000))+1;

        model.addAttribute("plan", plan);
        model.addAttribute("period", gap);
        return "/plan/detail";
    }

    @PostMapping("/checkList.do")
    public @ResponseBody PlanCheckListsDto tdlInset(
            PlanCheckListsDto planCheckListsDto)
    {
        int register = planCheckListsService.register(planCheckListsDto);
        return planCheckListsDto;
    }
    @DeleteMapping("/checkList.do")
    public @ResponseBody int tdlDelete(
            @RequestBody String clId)
    {
        return planCheckListsService.remove(Integer.parseInt(clId));
    }
    @PutMapping("/checkList.do")
    public @ResponseBody PlanCheckListsDto tdlModify(
            PlanCheckListsDto dto)
    {
        log.info("체크테스트1"+dto);
        if(Objects.equals(dto.getCheckStatus(), "UNCHECKED")) {
            dto.setCheckStatus("CHECKED");
        }
        else if (Objects.equals(dto.getCheckStatus(), "CHECKED")) {
            dto.setCheckStatus("UNCHECKED");
        }
        log.info("체크테스트2"+dto);
        planCheckListsService.modify(dto);
        return dto;
    }
}

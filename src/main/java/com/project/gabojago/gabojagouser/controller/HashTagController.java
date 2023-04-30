package com.project.gabojago.gabojagouser.controller;

import com.project.gabojago.gabojagouser.dto.HashTagDto;
import com.project.gabojago.gabojagouser.service.HashTagService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hashtag")
@AllArgsConstructor
public class HashTagController {
    private HashTagService hashTagService;

    @GetMapping("/{tag}/search.do")
    public List<HashTagDto> search(@PathVariable String tag){
        List<HashTagDto> tags=hashTagService.search(tag);
        return tags;
    }
}

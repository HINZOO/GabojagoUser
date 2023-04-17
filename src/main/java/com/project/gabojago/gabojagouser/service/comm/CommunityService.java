package com.project.gabojago.gabojagouser.service.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommImgDto;
import com.project.gabojago.gabojagouser.dto.comm.CommunityDto;

import java.util.List;

public interface CommunityService {
    //리스트 //상세 //등록 //수정 //삭제
    List<CommunityDto> list();
    List<CommImgDto> imgList(int[] ciId);
    CommunityDto detail(int cId);
    int register(CommunityDto community);
    int modify(CommunityDto community, int[] delImgIds);
    int remove(int cId);

}

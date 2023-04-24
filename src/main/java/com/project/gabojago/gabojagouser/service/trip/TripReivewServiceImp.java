package com.project.gabojago.gabojagouser.service.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripReviewDto;
import com.project.gabojago.gabojagouser.dto.trip.TripReviewImgDto;
import com.project.gabojago.gabojagouser.mapper.trip.TripReivewImgMapper;
import com.project.gabojago.gabojagouser.mapper.trip.TripReviewMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TripReivewServiceImp implements TripReviewService {

    private TripReviewMapper tripReviewMapper;
    private TripReivewImgMapper tripReivewImgMapper;




    @Override
    public List<TripReviewDto> list(int tId) {
        List<TripReviewDto> list=tripReviewMapper.findByTId(tId);
        return list;
    }

    @Override
    public TripReviewDto detail(int trId) {
        TripReviewDto detail=tripReviewMapper.findByTrId(trId);
        return detail;
    }

    // 🍒수정시 이미지 삭제하려고, 이미지 리스트 불러오기
    @Override
    public List<TripReviewImgDto> imgList(int[] triId) {
        List<TripReviewImgDto> imgList=null;
        if(triId!=null) {
            imgList=new ArrayList<>();
            for(int id : triId) {
                TripReviewImgDto imgDto=tripReivewImgMapper.findByTriId(id);
                imgList.add(imgDto);
            }
        }
        return imgList;
    }

    // 리뷰 등록 실패시 이미지 제거
    // 트립리뷰 이미지 // 이미지 등록 => 🍒db 은 저장 서비스에서!!!!
    @Override
    public int register(TripReviewDto tripReview) {
        int register=tripReviewMapper.insertOne(tripReview);
        if(tripReview.getImgs()!=null){ // 🍒컨트롤러에서 이미지를 패스에서 저장한것을 getImgs() 로 불러오는 것 // register 서비스 사용시, 이미지를 db 에 저장하는 코드
            for(TripReviewImgDto img : tripReview.getImgs()){
                img.setTrId(tripReview.getTrId());
                register+=tripReivewImgMapper.insertOne(img);
            }
        }
        return register;
    }

    @Override
    public int modify(TripReviewDto tripReview, int[] delImgIds) {
        // 내용 수정 + 이미지 등록 + 삭제
        int modify=tripReviewMapper.updateOne(tripReview);
        if(tripReview.getImgs()!=null){
            for(TripReviewImgDto img : tripReview.getImgs()){
                img.setTrId(tripReview.getTrId());
                modify+=tripReivewImgMapper.insertOne(img);
            }
        }
        if(delImgIds!=null){
            for(int triId : delImgIds){
                modify+=tripReivewImgMapper.deleteOne(triId);
            }
        }
        return modify;
    }


    @Override
    public int remove(int trId) {
        int remove=tripReviewMapper.deleteOne(trId);
        return remove;
    }



}

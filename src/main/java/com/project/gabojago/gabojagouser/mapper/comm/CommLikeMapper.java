package com.project.gabojago.gabojagouser.mapper.comm;
import com.project.gabojago.gabojagouser.dto.comm.CommLikeDto;
import com.project.gabojago.gabojagouser.dto.comm.LikeStatusCntDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface CommLikeMapper {
    boolean findByCIdAndUId(@Param("cId")int cId, @Param("uId")String uId);
    boolean findByCIdAndUIdIsLoginUserId(@Param("cId")int cId);//로그인한 유저가 좋아요 한 내역

    LikeStatusCntDto countStatusByCId(int cId);
    LikeStatusCntDto countStatusByUId(String uId);
    int insertOne(CommLikeDto boardLikeDto);

    int deleteOne(CommLikeDto boardLikeDto);


}
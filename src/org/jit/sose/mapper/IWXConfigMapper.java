package org.jit.sose.mapper;

import org.apache.ibatis.annotations.Param;
import org.jit.sose.entity.BannerPic;
import org.jit.sose.entity.IconInfo;
import org.jit.sose.entity.Notice;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IWXConfigMapper {
    List<BannerPic> getBannerPics();
    int getOpenIdCount(String openID);
    void insertOpenIDAndUserId(String openID,String userID);
    List<IconInfo> getIcon(@Param("page")String page,@Param("role")String role);
    List<Notice> getNotice(@Param("role")String role);


}

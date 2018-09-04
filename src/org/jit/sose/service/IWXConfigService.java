package org.jit.sose.service;

import org.jit.sose.entity.BannerPic;
import org.jit.sose.entity.IconInfo;
import org.jit.sose.entity.Notice;

import java.util.List;

public interface IWXConfigService {
    List<BannerPic> getBannerPics();
    int getOpenIdCount(String openID);
    void insertOpenIDAndUserId(String openID,String userID);
    List<IconInfo> getIcon(String page,String role);
    List<Notice> getNotice(String role);
}

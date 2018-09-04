package org.jit.sose.service.impl;

import org.jit.sose.entity.BannerPic;
import org.jit.sose.entity.IconInfo;
import org.jit.sose.entity.Notice;
import org.jit.sose.mapper.IWXConfigMapper;
import org.jit.sose.service.IWXConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WXconfigService implements IWXConfigService{

    @Autowired
    IWXConfigMapper iwxConfigMapper;

    public List<BannerPic> getBannerPics() {
        return iwxConfigMapper.getBannerPics();
    }

    public int getOpenIdCount(String openID) {
        return iwxConfigMapper.getOpenIdCount(openID);
    }

    public void insertOpenIDAndUserId(String openID, String userID) {
        iwxConfigMapper.insertOpenIDAndUserId(openID,userID);
    }

	public List<IconInfo> getIcon(String page, String role) {
		return iwxConfigMapper.getIcon(page, role);
	}

	@Override
	public List<Notice> getNotice(String role) {
		return iwxConfigMapper.getNotice(role);
	}

}

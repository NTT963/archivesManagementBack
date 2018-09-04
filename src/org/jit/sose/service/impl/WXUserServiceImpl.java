package org.jit.sose.service.impl;

import java.util.List;

import org.jit.sose.entity.WXUser;
import org.jit.sose.mapper.WXUserMapper;
import org.jit.sose.service.WXUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WXUserServiceImpl implements WXUserService{
	
	@Autowired
	private WXUserMapper wxUserMapper;

	@Override
	public boolean insertOpenIDAndUserId(WXUser wxUser) {
		if (wxUserMapper.insert(wxUser)>0) {
			return true;
		}
		return false;
	}

	@Override
	public int getOpenIdCount(String openId) {	
		return wxUserMapper.getOpenIdCount(openId);
	}

	@Override
	public List<WXUser> getUserInfo(String openId) {
		return wxUserMapper.getUserInfo(openId);
	}

}

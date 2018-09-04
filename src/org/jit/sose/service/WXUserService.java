package org.jit.sose.service;

import java.util.List;

import org.jit.sose.entity.WXUser;

public interface WXUserService {

	boolean insertOpenIDAndUserId(WXUser wxUser);

	int getOpenIdCount(String openId);

	List<WXUser> getUserInfo(String openId);

}

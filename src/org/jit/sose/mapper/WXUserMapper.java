package org.jit.sose.mapper;

import java.util.List;

import org.jit.sose.entity.WXUser;

public interface WXUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WXUser record);

    int insertSelective(WXUser record);

    WXUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WXUser record);

    int updateByPrimaryKey(WXUser record);

	int getOpenIdCount(String openId);
	
	List<WXUser> getUserInfo(String openId);
}
package org.jit.sose.service.impl;

import java.util.List;

import org.jit.sose.entity.WXPagePic;
import org.jit.sose.mapper.WXPagePicMapper;
import org.jit.sose.service.WXPagePicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WXPagePicImpl implements WXPagePicService{
	
	@Autowired
	private WXPagePicMapper WXPagePicMapper;

	@Override
	public List<WXPagePic> getBannerPics() {
		return WXPagePicMapper.getBannerPics();
	}

}

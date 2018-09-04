package org.jit.sose.service.impl;

import org.jit.sose.entity.UserArchive;
import org.jit.sose.mapper.UserArchiveMapper;
import org.jit.sose.service.UserArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserArchiveImpl implements UserArchiveService{
	
	
	@Autowired
	private UserArchiveMapper UserArchiveMapper;

	@Override
	public boolean insertUserArchive(UserArchive userArchive) {
		if (UserArchiveMapper.insertSelective(userArchive)>0) {
			return true;
		}
		return false;
	}

}

package org.jit.sose.service.impl;

import org.jit.sose.entity.Admin;
import org.jit.sose.mapper.AdminMapper;
import org.jit.sose.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl implements AdminService{
	
	
	@Autowired
	private AdminMapper AdminMapper;

	@Override
	public boolean isbe(Admin admin) {
		System.out.println(admin);
		Admin admin2 = AdminMapper.selectByNumAndName(admin);
		if (admin2.getId()>0) {
			return true;
		}
		return false;
	}

}

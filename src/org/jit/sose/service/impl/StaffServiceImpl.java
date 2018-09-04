package org.jit.sose.service.impl;

import org.jit.sose.entity.Staff;
import org.jit.sose.mapper.StaffMapper;
import org.jit.sose.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("staffService")
public class StaffServiceImpl implements StaffService{
	
	@Autowired
	private StaffMapper staffMapper;
	
	@Override
	public Staff fingLogin(Staff staff) {
		return staffMapper.findLogin(staff);
	}

	@Override
	public Staff getByLoginName(String loginName) {
		return staffMapper.getByLoginName(loginName);
	}

	@Override
	public int setLoginFailedTimes(Staff staff) {
		return staffMapper.setLoginFailedTimes(staff);
	}

	@Override
	public int setLocked(String loginName) {
		return staffMapper.setLocked(loginName);
	}

	@Override
	public int resetLoginFailedTimes(String loginName) {
		return staffMapper.resetLoginFailedTimes(loginName);
	}

	@Override
	public int resetPassword(Staff staff) {
		// TODO Auto-generated method stub
		return staffMapper.resetPassword(staff);
	}

	@Override
	public int insertStaff(Staff staff) {
		// TODO Auto-generated method stub
		return staffMapper.insertStaff(staff);
	}
}

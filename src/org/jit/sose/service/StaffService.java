package org.jit.sose.service;

import org.jit.sose.entity.Staff;

public interface StaffService {
	public Staff fingLogin(Staff staff);
	
	public Staff getByLoginName(String loginName);
	
	public int setLoginFailedTimes(Staff staff);
	
	public int setLocked(String loginName);
	
	public int resetLoginFailedTimes(String loginName);
	
	public int resetPassword(Staff staff);
	
	public int insertStaff(Staff staff);
}

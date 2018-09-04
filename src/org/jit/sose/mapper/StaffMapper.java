package org.jit.sose.mapper;
import org.jit.sose.entity.Staff;

public interface StaffMapper {
	public Staff findLogin(Staff staff);
	/**
	 * 通过用户名查找用户,之所以不使用用户名密码同时匹配的方式是因为
	 * 在表字段中存在一个字段为用户登录失败次数
	 */
	public Staff getByLoginName(String loginName);
	//设置用户登录失败次数,根据用户名设置
	public int setLoginFailedTimes(Staff staff);
	//设置用户是否锁定
	public int setLocked(String loginName);
	//重置用户登录失败次数,当用户在规定次数内成功之后需要重置，以免影响用户下次登录
	public int resetLoginFailedTimes(String loginName);
	
	public int resetPassword(Staff staff);
	
	//注册
	public int insertStaff(Staff staff);
}
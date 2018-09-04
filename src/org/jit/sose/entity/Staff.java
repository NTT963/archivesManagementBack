package org.jit.sose.entity;

import java.util.Date;

public class Staff {
    private String loginName;

    private String password;

    private Byte roleId;

    private Byte organizationId;

    private String departmentId;

    private String staffName;

    private String phone;

    private String email;

    private Date createTime;

    private String state;

    private Date stateTime;

    private String isLocked;

    private Integer loginFailedTimes;

    private String isLogin;

    private Date lastActiveTime;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Byte getRoleId() {
		return roleId;
	}

	public void setRoleId(Byte roleId) {
		this.roleId = roleId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public Byte getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Byte organizationId) {
		this.organizationId = organizationId;
	}

	

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getStateTime() {
		return stateTime;
	}

	public void setStateTime(Date stateTime) {
		this.stateTime = stateTime;
	}

	public String getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	public Integer getLoginFailedTimes() {
		return loginFailedTimes;
	}

	public void setLoginFailedTimes(Integer loginFailedTimes) {
		this.loginFailedTimes = loginFailedTimes;
	}

	public String getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}

	public Date getLastActiveTime() {
		return lastActiveTime;
	}

	public void setLastActiveTime(Date lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}
}
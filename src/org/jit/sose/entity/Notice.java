package org.jit.sose.entity;

public class Notice {
	
	private int ID;
	private String title;
	private String content;
	private String creatTime;
	private String state;
	private String noticeOrder;
	private String remark;
	private String role;
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getNoticeOrder() {
		return noticeOrder;
	}
	public void setNoticeOrder(String noticeOrder) {
		this.noticeOrder = noticeOrder;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Notice() {
	}
	
}

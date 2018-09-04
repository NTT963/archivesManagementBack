package org.jit.sose.entity;

import java.util.Date;

public class ArchiveReservation {
    private Integer id;

    private String userid;

    private Date rvdate;

    private Date rvstarttime;

    private Date rvendtime;

    private String rvthing;

    private String rvplace;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public Date getRvdate() {
        return rvdate;
    }

    public void setRvdate(Date rvdate) {
        this.rvdate = rvdate;
    }

    public Date getRvstarttime() {
        return rvstarttime;
    }

    public void setRvstarttime(Date rvstarttime) {
        this.rvstarttime = rvstarttime;
    }

    public Date getRvendtime() {
        return rvendtime;
    }

    public void setRvendtime(Date rvendtime) {
        this.rvendtime = rvendtime;
    }

    public String getRvthing() {
        return rvthing;
    }

    public void setRvthing(String rvthing) {
        this.rvthing = rvthing == null ? null : rvthing.trim();
    }

    public String getRvplace() {
        return rvplace;
    }

    public void setRvplace(String rvplace) {
        this.rvplace = rvplace == null ? null : rvplace.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	@Override
	public String toString() {
		return "ArchiveReservation [id=" + id + ", userid=" + userid + ", rvdate=" + rvdate + ", rvstarttime="
				+ rvstarttime + ", rvendtime=" + rvendtime + ", rvthing=" + rvthing + ", rvplace=" + rvplace
				+ ", remark=" + remark + "]";
	}
    
    
}
package org.jit.sose.entity;

public class WXUser {
    private Integer id;

    private String openid;

    private String userid;

    private String usercard;

    private Integer state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getUsercard() {
        return usercard;
    }

    public void setUsercard(String usercard) {
        this.usercard = usercard == null ? null : usercard.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

	@Override
	public String toString() {
		return "WXUser [id=" + id + ", openid=" + openid + ", userid=" + userid + ", usercard=" + usercard + ", state="
				+ state + "]";
	}
    
    
}
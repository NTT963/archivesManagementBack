package org.jit.sose.entity;

public class WXPagePic {
    private Integer id;

    private String title;

    private String url;

    private Integer picorder;

    private Integer state;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getPicorder() {
        return picorder;
    }

    public void setPicorder(Integer picorder) {
        this.picorder = picorder;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	@Override
	public String toString() {
		return "WXPagePic [id=" + id + ", title=" + title + ", url=" + url + ", picorder=" + picorder + ", state="
				+ state + ", remark=" + remark + "]";
	}
    
    
}
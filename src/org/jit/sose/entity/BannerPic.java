package org.jit.sose.entity;

import org.springframework.stereotype.Component;

@Component
public class BannerPic {
    private int ID;
    private String title;
    private String url;
    private int picOrder;
    private int state;
    private String remark;

    public BannerPic() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPicOrder() {
        return picOrder;
    }

    public void setPicOrder(int picOrder) {
        this.picOrder = picOrder;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

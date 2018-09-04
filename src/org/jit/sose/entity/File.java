package org.jit.sose.entity;

import java.util.Date;

public class File {
    private Integer fileid;

    private String filenewname;

    private String fileoriginname;

    private Integer fileownerid;

    private Date filetime;

    private String fileurl;

    private String filestatus;

    public Integer getFileid() {
        return fileid;
    }

    public void setFileid(Integer fileid) {
        this.fileid = fileid;
    }

    public String getFilenewname() {
        return filenewname;
    }

    public void setFilenewname(String filenewname) {
        this.filenewname = filenewname == null ? null : filenewname.trim();
    }

    public String getFileoriginname() {
        return fileoriginname;
    }

    public void setFileoriginname(String fileoriginname) {
        this.fileoriginname = fileoriginname == null ? null : fileoriginname.trim();
    }

    public Integer getFileownerid() {
        return fileownerid;
    }

    public void setFileownerid(Integer fileownerid) {
        this.fileownerid = fileownerid;
    }

    public Date getFiletime() {
        return filetime;
    }

    public void setFiletime(Date filetime) {
        this.filetime = filetime;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl == null ? null : fileurl.trim();
    }

    public String getFilestatus() {
        return filestatus;
    }

    public void setFilestatus(String filestatus) {
        this.filestatus = filestatus == null ? null : filestatus.trim();
    }
}
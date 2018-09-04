package org.jit.sose.entity;

public class UserArchive {
    private Integer id;

    private String userid;

    private String archiveid;

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

    public String getArchiveid() {
        return archiveid;
    }

    public void setArchiveid(String archiveid) {
        this.archiveid = archiveid == null ? null : archiveid.trim();
    }

	@Override
	public String toString() {
		return "UserArchive [id=" + id + ", userid=" + userid + ", archiveid=" + archiveid + "]";
	}
    
}
package org.jit.sose.entity;

public class Admin {
    private Integer id;

    private String adminnum;

    private String adminname;

    private String adminidcart;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdminnum() {
        return adminnum;
    }

    public void setAdminnum(String adminnum) {
        this.adminnum = adminnum == null ? null : adminnum.trim();
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname == null ? null : adminname.trim();
    }

    public String getAdminidcart() {
        return adminidcart;
    }

    public void setAdminidcart(String adminidcart) {
        this.adminidcart = adminidcart == null ? null : adminidcart.trim();
    }

	@Override
	public String toString() {
		return "Admin [id=" + id + ", adminnum=" + adminnum + ", adminname=" + adminname + ", adminidcart="
				+ adminidcart + "]";
	}
    
    
    
}
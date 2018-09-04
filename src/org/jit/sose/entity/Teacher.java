package org.jit.sose.entity;

public class Teacher {
    private Integer id;

    private String tchnum;

    private String tchidcard;

    private String tchname;

    private String sex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTchnum() {
        return tchnum;
    }

    public void setTchnum(String tchnum) {
        this.tchnum = tchnum == null ? null : tchnum.trim();
    }

    public String getTchidcard() {
        return tchidcard;
    }

    public void setTchidcard(String tchidcard) {
        this.tchidcard = tchidcard == null ? null : tchidcard.trim();
    }

    public String getTchname() {
        return tchname;
    }

    public void setTchname(String tchname) {
        this.tchname = tchname == null ? null : tchname.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", tchnum=" + tchnum + ", tchidcard=" + tchidcard + ", tchname=" + tchname
				+ ", sex=" + sex + "]";
	}
    
    
    
}
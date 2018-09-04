package org.jit.sose.entity;

public class Student {
    private Integer id;

    private String stunum;

    private String stuidcard;

    private String stuclass;

    private String stuname;

    private String sex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStunum() {
        return stunum;
    }

    public void setStunum(String stunum) {
        this.stunum = stunum == null ? null : stunum.trim();
    }

    public String getStuidcard() {
        return stuidcard;
    }

    public void setStuidcard(String stuidcard) {
        this.stuidcard = stuidcard == null ? null : stuidcard.trim();
    }

    public String getStuclass() {
        return stuclass;
    }

    public void setStuclass(String stuclass) {
        this.stuclass = stuclass == null ? null : stuclass.trim();
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname == null ? null : stuname.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

	@Override
	public String toString() {
		return "Student [id=" + id + ", stunum=" + stunum + ", stuidcard=" + stuidcard + ", stuclass=" + stuclass
				+ ", stuname=" + stuname + ", sex=" + sex + "]";
	}
    
    
}
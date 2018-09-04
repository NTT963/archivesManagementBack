package org.jit.sose.entity;

public class ClassifyAdmin {
	private String adminID;
    private String adminRole;
    private String staffName;
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return this.adminID+" ==>"+ this.adminRole + "==>  " + this.staffName;
    }
    
	public String getAdminID() {
		return adminID;
	}
	public void setAdminID(String adminID) {
		this.adminID = adminID;
	}
	public String getAdminRole() {
		return adminRole;
	}
	public void setAdminRole(String adminRole) {
		this.adminRole = adminRole;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public ClassifyAdmin() {
		super();
	}
    
    
	

}

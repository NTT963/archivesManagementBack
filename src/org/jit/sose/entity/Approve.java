package org.jit.sose.entity;

import com.sun.xml.internal.bind.v2.model.core.ID;

public class Approve {
    private int ID;
    private String approver;
    private String approveTime;
    private String archiveID;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Approve() {
    }

    public Approve(String approver, String archiveID, String state) {
        this.approver = approver;
        this.archiveID = archiveID;
        this.state = state;
    }

    public String getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }

    public int getID() {

        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getArchiveID() {
        return archiveID;
    }

    public void setArchiveID(String archiveID) {
        this.archiveID = archiveID;
    }
}

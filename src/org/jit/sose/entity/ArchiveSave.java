package org.jit.sose.entity;

import com.sun.xml.internal.bind.v2.model.core.ID;

public class ArchiveSave {
    private int ID;
    private String userID;
    private String archiveID;
    private String saveTime;

    public ArchiveSave() {
    }

    public ArchiveSave(String userID, String archiveID) {
        this.userID = userID;
        this.archiveID = archiveID;
       
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }
}

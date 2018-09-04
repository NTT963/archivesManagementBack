package org.jit.sose.entity;

import com.sun.xml.internal.bind.v2.model.core.ID;

public class ArchiveSave {
    private int ID;
    private String userID;
    private String archiveName;
    private String url;
    private String saveTime;

    public ArchiveSave() {
    }

    public ArchiveSave(String userID, String archiveName, String url) {
        this.userID = userID;
        this.archiveName = archiveName;
        this.url = url;
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

    public String getArchiveName() {
        return archiveName;
    }

    public void setArchiveName(String archiveName) {
        this.archiveName = archiveName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }
}

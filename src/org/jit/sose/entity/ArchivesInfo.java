package org.jit.sose.entity;

public class ArchivesInfo {
    private int ID;
    private String classifyId;
    private String archivesId;
    private String archivesName;
    private String content;
    private String url;
    private String state;
    private String uploadTime;
    private String uploadUserId;

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(String uploadUserId) {
        this.uploadUserId = uploadUserId;
    }
    //    ID	classifyId	archivesId	archivesName	content	url	state

    public ArchivesInfo() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArchivesInfo(String classifyId, String archivesId, String archivesName, String content, String url, String state, String uploadTime, String uploadUserId) {

        this.classifyId = classifyId;
        this.archivesId = archivesId;
        this.archivesName = archivesName;
        this.content = content;
        this.url = url;
        this.state = state;
        this.uploadTime = uploadTime;
        this.uploadUserId = uploadUserId;
    }

    @Override
    public String toString() {
        return "ArchivesInfo{" +
                "ID=" + ID +
                ", classifyId='" + classifyId + '\'' +
                ", archivesId='" + archivesId + '\'' +
                ", archivesName='" + archivesName + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }

    public String getArchivesName() {
        return archivesName;
    }

    public void setArchivesName(String archivesName) {
        this.archivesName = archivesName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

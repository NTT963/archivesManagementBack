package org.jit.sose.entity;

import java.util.List;

/**
 * 目录中的每个节点
 */
public class TreeNode {
    private int ID;
    private String classifyId;
    private String title;
    /*private int classifyLevel;*/
    private String classifyFatherId;
    private List<TreeNode> children;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "classifyId='" + classifyId + '\'' +
                ", classifyName='" + title + '\'' +
               
                ", classifyFatherId='" + classifyFatherId + '\'' +
                ", children=" + children +
                '}';
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public TreeNode() {
    }

    public TreeNode(String classifyId, String title, String classifyFatherId) {
        this.classifyId = classifyId;
        this.title = title;
        this.classifyFatherId = classifyFatherId;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getClassifyFatherId() {
        return classifyFatherId;
    }

    public void setClassifyFatherId(String classifyFatherId) {
        this.classifyFatherId = classifyFatherId;
    }
}

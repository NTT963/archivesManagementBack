package org.jit.sose.service;

import org.jit.sose.entity.ClassifyAdmin;
import org.jit.sose.entity.Teacher;
import org.jit.sose.entity.TreeNode;

import java.util.List;

public interface ITreeService {

    List<TreeNode> getAllTreeNode();
    int addTreeNode(TreeNode node);
    void deleteTreeNode(String classifyId);
    void updateTreeNOde(TreeNode node);
    TreeNode getTreeNodeByClassifyId(String classifyId);
    List<TreeNode> getTreeNodeByClassifyFatherId(String classifyFatherId);
    void insertAdmin(int classifyID,String adminID,String adminRole);
    void deleteAdmin(String ID);
    List<ClassifyAdmin> getAdminyByClassifyID(int classifyID);
    List<String> getAllTeacher();
}

package org.jit.sose.mapper;

import org.apache.ibatis.annotations.Param;
import org.jit.sose.entity.ClassifyAdmin;
import org.jit.sose.entity.Teacher;
import org.jit.sose.entity.TreeNode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ITreeMapper {

    List<TreeNode> getAllTreeNode();
    TreeNode getTreeNodeByClassifyId(String classifyId);
    List<TreeNode> getTreeNodeByClassifyFatherId(String classifyFatherId);
    int addTreeNode(TreeNode node);
    void deleteTreeNode(String classifyId);
    void updateTreeNOde(TreeNode node);
    void insertAdmin(@Param("classifyID") int classifyID, @Param("adminID") String adminID, @Param("adminRole") String adminRole);
    void deleteAdmin(String ID);
    List<ClassifyAdmin> getAdminyByClassifyID(int classifyID);
    List<String> getAllTeacher();
}

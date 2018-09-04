package org.jit.sose.service.impl;

import java.util.List;

import org.jit.sose.entity.Teacher;
import org.jit.sose.entity.TreeNode;
import org.jit.sose.mapper.ITreeMapper;
import org.jit.sose.entity.ClassifyAdmin;
import org.jit.sose.service.ITreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TreeService implements ITreeService {
    @Autowired
    private ITreeMapper iTreeMapper;

    public List<TreeNode> getAllTreeNode() {
        return iTreeMapper.getAllTreeNode();
    }

    @Override
    public int addTreeNode(TreeNode node) {
        return iTreeMapper.addTreeNode(node);
    }


    public void deleteTreeNode(String classifyId) {
        List<TreeNode> childNode = iTreeMapper.getTreeNodeByClassifyFatherId(classifyId);
        for (TreeNode treeNode : childNode) {
            iTreeMapper.deleteTreeNode(treeNode.getClassifyId());
        }
        iTreeMapper.deleteTreeNode(classifyId);
    }


    public void updateTreeNOde(TreeNode node) {
        iTreeMapper.updateTreeNOde(node);
    }

    public TreeNode getTreeNodeByClassifyId(String classifyId) {
        return iTreeMapper.getTreeNodeByClassifyId(classifyId);
    }

    public List<TreeNode> getTreeNodeByClassifyFatherId(String classifyFatherId) {
        return iTreeMapper.getTreeNodeByClassifyFatherId(classifyFatherId);
    }

    public void insertAdmin(int classifyID, String adminID, String adminRole) {
        iTreeMapper.insertAdmin(classifyID,adminID,adminRole);
    }

    @Override
    public void deleteAdmin(String ID) {
        iTreeMapper.deleteAdmin(ID);
    }

    @Override
	public List<ClassifyAdmin> getAdminyByClassifyID(int classifyID) {
		// TODO Auto-generated method stub
		return iTreeMapper.getAdminyByClassifyID(classifyID);
	}

    @Override
    public List<String> getAllTeacher() {
        return iTreeMapper.getAllTeacher();
    }

}

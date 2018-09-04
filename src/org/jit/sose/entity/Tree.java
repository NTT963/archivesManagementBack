package org.jit.sose.entity;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    List<TreeNode> nodes = new ArrayList<TreeNode>();
//    TreeService treeService = SpringUtil.getBean(TreeService.class);


    public JSONArray buildTree(List<TreeNode> nodes) {

        Tree treeBuilder = new Tree(nodes);

        return treeBuilder.buildJSONTree();
    }

    public List<TreeNode> buildListTree(List<TreeNode> nodes){
        Tree treeBuilder = new Tree(nodes);

        return treeBuilder.buildTree();

    }

    // 构建JSON树形结构
    public JSONArray buildJSONTree() {
        List<TreeNode> nodeTree = buildTree();
        JSONArray jsonArray = (JSONArray) JSONArray.toJSON(nodeTree);
        return jsonArray;
    }

    // 构建树形结构
    public List<TreeNode> buildTree() {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        List<TreeNode> rootNodes = getRootNodes();
        for (TreeNode rootNode : rootNodes) {
            buildChildNodes(rootNode);
            treeNodes.add(rootNode);
        }
        return treeNodes;
    }

    // 递归子节点
    public void buildChildNodes(TreeNode node) {
        List<TreeNode> children = getChildNodes(node);
        if (!children.isEmpty()) {
            for (TreeNode child : children) {
                buildChildNodes(child);
            }
            node.setChildren(children);

            System.out.println("不是叶子节点"+ node.getID() +node.getChildren().size() + " " + node.getTitle());
//            treeService.deleteAdmin(String.valueOf(node.getID()));
        }else {
            System.out.println("叶子节点==>"+ node.getTitle());
        }
    }

    // 判断是否为根节点
    public boolean rootNode(TreeNode node) {
        if (node.getClassifyFatherId().equals("0"))
            return true;
        else
            return false;
    }

    // 获取集合中所有的根节点
    public List<TreeNode> getRootNodes() {
        List<TreeNode> rootNodes = new ArrayList<TreeNode>();
        for (TreeNode n : nodes) {
            if (rootNode(n)) {
                rootNodes.add(n);
            }
        }
        return rootNodes;
    }

    // 获取父节点下所有的子节点
    public List<TreeNode> getChildNodes(TreeNode fatherNode) {
        List<TreeNode> childNodes = new ArrayList<TreeNode>();
        for (TreeNode n : nodes) {
            if (fatherNode.getClassifyId().equals(n.getClassifyFatherId())) {
                childNodes.add(n);
            }
        }
        return childNodes;
    }

    public Tree() {
    }

    public Tree(List<TreeNode> nodes) {
        this.nodes = nodes;
    }
}

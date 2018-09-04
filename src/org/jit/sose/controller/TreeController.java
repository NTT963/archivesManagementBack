package org.jit.sose.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import org.jit.sose.entity.ClassifyAdmin;
import org.jit.sose.entity.Teacher;
import org.jit.sose.entity.Tree;
import org.jit.sose.entity.TreeNode;
import org.jit.sose.service.impl.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class TreeController {
    @Autowired
    TreeService treeService;

    @RequestMapping(value = "/getAllTreeNode.do")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public JSONArray getAllTreeNode() {
        List<TreeNode> treeNodes = treeService.getAllTreeNode();
        JSONArray jsonArray = new Tree().buildTree(treeNodes);
        return jsonArray;
    }

    @RequestMapping(value = "/getCascaderData.do")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public JSONArray getCascaderData() {
        List<TreeNode> treeNodes = treeService.getAllTreeNode();
        JSONArray jsonArray = new Tree().buildTree(treeNodes);
        String string = JSONObject.toJSONString(jsonArray).replaceAll("classifyId", "value");
        JSONArray result = JSONArray.parseArray(string.replaceAll("title", "label"));
        return result;
    }

    @RequestMapping(value = "/getAllTeacher.do")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)

    public List<String> getAllTeacher() {
        List<String> teachers = treeService.getAllTeacher();
        System.out.println(teachers.size());
        return teachers;
    }

    @RequestMapping(value = "/getTreeNodeDetail.do")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public JSONObject getTreeNodeDetail(int id) {
        System.out.println("获取管理员"+id);
        List<ClassifyAdmin> list = treeService.getAdminyByClassifyID(id);
        Map<String, List<ClassifyAdmin>> resultMap = new HashMap<String, List<ClassifyAdmin>>();
        for (ClassifyAdmin admin : list) {
            if (resultMap.containsKey(admin.getAdminRole())) {
                resultMap.get(admin.getAdminRole()).add(admin);
            } else {
                List<ClassifyAdmin> tmpList = new ArrayList<ClassifyAdmin>();
                tmpList.add(admin);
                resultMap.put(admin.getAdminRole(), tmpList);
            }
        }
        JSONObject detail = (JSONObject) JSONObject.toJSON(resultMap);
        return detail;

    }


    @RequestMapping(value = "/addTreeNode.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public JSONObject addTreeNode(@RequestBody String paramTreeNode) {
        JSONObject result = new JSONObject();
        JSONObject params = JSONObject.parseObject(paramTreeNode);
        TreeNode treeNode = JSON.parseObject(params.get("treNode").toString(), new TypeReference<TreeNode>() {
        });
        treeService.addTreeNode(treeNode);
        int classifyId = treeNode.getID();
        result.put("ID",classifyId);
        System.out.println("返回主键" + treeNode.getID());
        JSONArray admins = (JSONArray) params.get("admin");

        for (int i = 0; i < admins.size(); i++) {
            JSONObject admin = (JSONObject) admins.get(i);
            for (String key:admin.keySet()){
                treeService.insertAdmin(classifyId,admin.getString(key),key);
                System.out.println("插入管理员"+key);
            }
        }
        result.put("result","success");
        return result;
    }

    @RequestMapping(value = "/deleteTreeNode.do",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public void deleteTreeNode(@RequestBody String params) {
        System.out.println(params);
        JSONObject paramsObject = JSON.parseObject(params);
        treeService.deleteTreeNode(paramsObject.getString("classifyId"));
        treeService.deleteAdmin(paramsObject.getString("ID"));
    }

    @RequestMapping(value = "/updateTreeNode.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public void updateTreeNode(@RequestBody String paramTreeNode) {

        TreeNode treeNode = JSON.parseObject(paramTreeNode, new TypeReference<TreeNode>() {
        });
        System.out.println("调用更新接口" + treeNode.getTitle());
        treeService.updateTreeNOde(treeNode);

    }


}

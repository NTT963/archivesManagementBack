package org.jit.sose.controller;

import com.alibaba.fastjson.JSONObject;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.jit.sose.entity.ArchiveSave;
import org.jit.sose.entity.ArchivesInfo;
import org.jit.sose.entity.Staff;
import org.jit.sose.mapper.IArchivesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunwujun on 2018/9/6 11:11
 */
@Controller
@RequestMapping("mySave")
public class MySaveController {

    @Autowired
    IArchivesMapper iArchivesMapper;

    @RequestMapping(value = "insertArchiveSave.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Map<String, Object> insertArchiveSave(@RequestBody String insertInfo, HttpSession session) {
        JSONObject params = JSONObject.parseObject(insertInfo);
        System.out.println(params);
        Map<String, Object> resultMap = new HashMap<>();
        Staff staff = (Staff) session.getAttribute("STAFF_SESSION");
        String userID = staff.getLoginName();
        try {
            if (iArchivesMapper.insertArchiveSave(new ArchiveSave(userID, params.getString("archiveID"))) == 1) {
                resultMap.put("result", "success");
            } else {
                resultMap.put("result", "fail");
            }
        } catch (Exception e) {
            Throwable cause = e.getCause();
            System.out.println("=====" + cause.getMessage());
            if (cause instanceof MySQLIntegrityConstraintViolationException) {
                System.out.println("捕获异常成功");
                resultMap.put("result", "fail");
                resultMap.put("failMessage", "uniqueKeyError");
            }

        }
        return resultMap;
    }

    @RequestMapping(value = "cancleSaveArchive.do")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Map<String, Object> cancleSaveArchive(String archiveID, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>();
        Staff staff = (Staff) session.getAttribute("STAFF_SESSION");
        String userID = staff.getLoginName();
        System.out.println(archiveID + "/" + userID);
//        iArchivesMapper.cancleSaveArchive(new ArchiveSave("1712213023","17"));
        if (iArchivesMapper.cancleSaveArchive(new ArchiveSave(userID,archiveID))==1){
            resultMap.put("result","success");
        }else {
            resultMap.put("result","fail");
        }

        return resultMap;

    }

    @RequestMapping(value = "getMyUpload.do")
    @ResponseBody
    @CrossOrigin(origins = "*",maxAge = 3600)
    public Map<String,Object> getMyUpload(HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();

        Staff staff = (Staff) session.getAttribute("STAFF_SESSION");
        String userID = staff.getLoginName();
        List<ArchivesInfo> archivesInfos = iArchivesMapper.getMyUpload(userID);
        resultMap.put("list",archivesInfos);
        return resultMap;
    }

    @RequestMapping(value = "getArchivesSaveByUserId.do")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Map<String, Object> getArchivesSaveByUserId(String userID) {
        Map<String, Object> resultMap = new HashMap<>();
        List<ArchivesInfo> archivesInfos = iArchivesMapper.getArchiveSaves(userID);
        resultMap.put("archivesInfos", archivesInfos);
        return resultMap;
    }

}

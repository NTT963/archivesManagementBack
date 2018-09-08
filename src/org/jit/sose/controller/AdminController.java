package org.jit.sose.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.jit.sose.entity.Approve;
import org.jit.sose.entity.ArchivesInfo;
import org.jit.sose.entity.Staff;
import org.jit.sose.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunwujun on 2018/9/2 11:44
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    AdminMapper adminMapper;

    @RequestMapping(value = "/getArchiveWaitMeApprove.do")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Map<String, Object> getArchiveWaitMeApprove(String adminID, int page) {
        Map<String, Object> map = new HashMap<>();


        List<ArchivesInfo> archivesInfos = adminMapper.getArchiveWaitMeApprove(adminID);
        PageHelper.startPage(page, 10);
        PageInfo<ArchivesInfo> pageInfo = new PageInfo<>(archivesInfos);

        map.put("list", archivesInfos);
        map.put("total", pageInfo.getTotal());
        return map;
    }

    @RequestMapping(value = "/approveArchive.do")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public int approveArchive(int archiveId, HttpSession session) {
        Staff testS = (Staff) session.getAttribute("STAFF_SESSION");
        String approver = testS.getLoginName();
        System.out.println("session中的用户信息为：" + testS.getLoginName());
        if(adminMapper.updateArchiveState(archiveId) == 1){
            return adminMapper.insertApprove(new Approve(approver,String.valueOf(archiveId),"1"));
        }
        return 0;
    }
}
